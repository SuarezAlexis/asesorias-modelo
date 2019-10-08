/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.unam.dgtic.modelo.dto.SolicitanteDto;
import mx.unam.dgtic.modelo.dto.TipoSolicitanteDto;
import mx.unam.dgtic.modelo.util.Database;

/**
 *
 * @author alexis.suarez
 */
public class SolicitanteDaoJdbc implements SolicitanteDao {

    /****************************** CONSTANTES ********************************/
    private static final String INSERT_SQL = "INSERT INTO Solicitante(nombre,apellidos,tipo,contacto,habilitado) VALUES (?,?,?,?,?)";
    private static final String UPDATE_SQL = "UPDATE Solicitante SET nombre = ?, apellidos = ?, tipo = ?, contacto = ?, habilitado = ? WHERE id = ?";
    private static final String DELETE_SQL = "CALL sp_DeleteSolicitante(?)";
    private static final String SELECT_SQL = "SELECT S.*, T.descripcion, T.habilitado tipo_habilitado FROM Solicitante S JOIN TipoSolicitante T ON(T.nombre = S.tipo)";
    
    /****************************** ATRIBUTOS *********************************/
    private static Connection conn;

    /**************************** CONSTRUCTORES *******************************/
    public SolicitanteDaoJdbc() {
    }
    
    public SolicitanteDaoJdbc(Connection conn) {
        this.conn = conn;
    }
    
    /*************************** MÉTODOS PRIVADOS *****************************/
    /**
     * Genera un objeto de transferencia de datos a partir de un ResultSet
     * @return
     * @throws SQLException 
     */
    private static SolicitanteDto resultSetMapper(ResultSet rs) throws SQLException {
        SolicitanteDto dto = new SolicitanteDto();
        dto.setId(rs.getShort("id"));
        dto.setTipo(new TipoSolicitanteDto());
        dto.getTipo().setNombre(rs.getString("tipo"));
        dto.getTipo().setDescripcion(rs.getString("descripcion"));
        dto.getTipo().setHabilitado(rs.getBoolean("tipo_habilitado"));
        dto.setNombre(rs.getString("nombre"));
        dto.setApellidos(rs.getString("apellidos"));
        dto.setContacto(rs.getString("contacto"));
        dto.setHabilitado(rs.getBoolean("habilitado"));
        return dto;
    }
    
    /*************************** MÉTODOS PUBLICOS *****************************/
    @Override
    public SolicitanteDto guardar(SolicitanteDto solicitante) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            //Sentencia SQL a ejecutar
            String sql = solicitante.getId() < 0? INSERT_SQL : UPDATE_SQL;
            //Se crea el PreparedStatement
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Parámetros
            int i = 1; //Contador de parámetros
            ps.setString(i++, solicitante.getNombre());
            ps.setString(i++, solicitante.getApellidos());
            ps.setString(i++, solicitante.getTipo().getNombre());
            ps.setString(i++, solicitante.getContacto());
            ps.setBoolean(i++, solicitante.isHabilitado());
            if(solicitante.getId() >= 0) 
                ps.setInt(i, solicitante.getId());
            //Ejecución
            if( ps.executeUpdate() == 1 ) {
                if( solicitante.getId() < 0) {
                    //Se obtienen las llaves de auto-incremento generadas en un ResultSet
                    rs = ps.getGeneratedKeys();
                    //Se lee el primer registro
                    rs.first();
                    //Se obtiene el id de auto-incremento
                    solicitante.setId(rs.getInt(1));
                }
            }
            else {
                solicitante = null;
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(ps);
            if(this.conn != conn)
                Database.getInstance().close(conn);
        }
        return solicitante;
    }

    @Override
    public SolicitanteDto eliminar(int id) {
        SolicitanteDto solicitante = new SolicitanteDto();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            ps = conn.prepareCall(DELETE_SQL);
            ps.setInt(1, id);
            ps.execute();
            rs = ps.getResultSet();
            rs.first();
            solicitante = resultSetMapper(rs);
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(ps);
            if(conn != this.conn)
                Database.getInstance().close(conn);
        }
        return solicitante;
    }

    @Override
    public SolicitanteDto obtener(int id) {
        SolicitanteDto solicitante = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL + " WHERE S.id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rs.next();
            solicitante = resultSetMapper(rs);
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            if(conn != this.conn)
                Database.getInstance().close(conn);
        }
        return solicitante;
    }

    @Override
    public List<SolicitanteDto> obtenerTodos() {
        List<SolicitanteDto> solicitantes = new ArrayList<SolicitanteDto>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL);
            rs = ps.executeQuery();
            while(rs.next()){
                solicitantes.add(resultSetMapper(rs));
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            if(conn != this.conn)
                Database.getInstance().close(conn);
        }
        return solicitantes;
    }

    @Override
    public List<SolicitanteDto> obtenerHabilitados() {
        List<SolicitanteDto> solicitantes = new ArrayList<SolicitanteDto>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL + " WHERE S.habilitado = 1");
            rs = ps.executeQuery();
            while(rs.next()){
                solicitantes.add(resultSetMapper(rs));
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            if(conn != this.conn)
                Database.getInstance().close(conn);
        }
        return solicitantes;
    }
}
