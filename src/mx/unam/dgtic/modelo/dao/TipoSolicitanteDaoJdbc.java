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
import java.util.ArrayList;
import java.util.List;
import mx.unam.dgtic.modelo.dto.TipoSolicitanteDto;
import mx.unam.dgtic.modelo.util.Database;

/**
 *
 * @author alexis.suarez
 */
public class TipoSolicitanteDaoJdbc implements TipoSolicitanteDao {
    /****************************** CONSTANTES ********************************/
    private static final String INSERT_SQL = "INSERT IGNORE INTO TipoSolicitante(nombre,descripcion,habilitado) VALUES (?,?,?) ON DUPLICATE KEY UPDATE descripcion = ?, habilitado = ?";
    private static final String DELETE_SQL = "CALL sp_DeleteTipoSolicitante(?)";
    private static final String SELECT_SQL = "SELECT * FROM TipoSolicitante";
    
    /****************************** ATRIBUTOS *********************************/
    private static Connection conn;

    /**************************** CONSTRUCTORES *******************************/
    public TipoSolicitanteDaoJdbc() {
    }
    
    public TipoSolicitanteDaoJdbc(Connection conn) {
        this.conn = conn;
    }
    
    /*************************** MÉTODOS PRIVADOS *****************************/
    /**
     * Genera un objeto de transferencia de datos a partir de un ResultSet
     * @return
     * @throws SQLException 
     */
    private static TipoSolicitanteDto resultSetMapper(ResultSet rs) throws SQLException {
        TipoSolicitanteDto dto = new TipoSolicitanteDto();
        dto.setNombre(rs.getString("nombre"));
        dto.setDescripcion(rs.getString("descripcion"));
        dto.setHabilitado(rs.getBoolean("habilitado"));
        return dto;
    }
    
    /*************************** MÉTODOS PUBLICOS *****************************/
    @Override
    public TipoSolicitanteDto guardar(TipoSolicitanteDto tipo) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            //Sentencia SQL a ejecutar
            String sql = INSERT_SQL;
            
            //Se crea el PreparedStatement
            ps = conn.prepareStatement(sql);
            
            //Parámetros
            int i = 1; //Contador de parámetros
            ps.setString(i++, tipo.getNombre());
            ps.setString(i++, tipo.getDescripcion());
            ps.setBoolean(i++, tipo.isHabilitado());
            ps.setString(i++, tipo.getDescripcion());
            ps.setBoolean(i++, tipo.isHabilitado());

            //Ejecución
            if( ps.executeUpdate() < 1 )
                tipo = null;
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(ps);
            if(this.conn != conn)
                Database.getInstance().close(conn);
        }
        return tipo;
    }

    @Override
    public TipoSolicitanteDto eliminar(String nombre) {
        TipoSolicitanteDto tipo = new TipoSolicitanteDto();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            ps = conn.prepareCall(DELETE_SQL);
            ps.setString(1, nombre);
            ps.execute();
            rs = ps.getResultSet();
            rs.first();
            tipo = resultSetMapper(rs);
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(ps);
            if(conn != this.conn)
                Database.getInstance().close(conn);
        }
        return tipo;
    }

    @Override
    public TipoSolicitanteDto obtener(String nombre) {
        TipoSolicitanteDto tipo = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL + " WHERE nombre = ?");
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            rs.next();
            tipo = resultSetMapper(rs);
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            if(conn != this.conn)
                Database.getInstance().close(conn);
        }
        return tipo;
    }

    @Override
    public List<TipoSolicitanteDto> obtenerTodos() {
        List<TipoSolicitanteDto> tipos = new ArrayList<TipoSolicitanteDto>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL);
            rs = ps.executeQuery();
            while(rs.next()){
                tipos.add(resultSetMapper(rs));
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            if(conn != this.conn)
                Database.getInstance().close(conn);
        }
        return tipos;
    }

    @Override
    public List<TipoSolicitanteDto> obtenerHabilitados() {
        List<TipoSolicitanteDto> tipos = new ArrayList<TipoSolicitanteDto>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL + " WHERE habilitado = 1");
            rs = ps.executeQuery();
            while(rs.next()){
                tipos.add(resultSetMapper(rs));
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            if(conn != this.conn)
                Database.getInstance().close(conn);
        }
        return tipos;
    }
    
}
