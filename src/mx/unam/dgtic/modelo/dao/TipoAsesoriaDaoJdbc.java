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
import mx.unam.dgtic.modelo.dto.TipoAsesoriaDto;
import mx.unam.dgtic.modelo.util.Database;

/**
 *
 * @author alexis.suarez
 */
public class TipoAsesoriaDaoJdbc implements TipoAsesoriaDao {
/****************************** CONSTANTES ********************************/
    private static final String INSERT_SQL = "INSERT INTO TipoAsesoria(nombre,descripcion,habilitado) VALUES (?,?,?)";
    private static final String UPDATE_SQL = "UPDATE TipoAsesoria SET nombre = ?, descripcion = ?, habilitado = ? WHERE id = ?";
    private static final String DELETE_SQL = "CALL sp_DeleteTipoAsesoria(?)";
    private static final String SELECT_SQL = "SELECT * FROM TipoAsesoria";
    
    /****************************** ATRIBUTOS *********************************/
    private static Connection conn;

    /**************************** CONSTRUCTORES *******************************/
    public TipoAsesoriaDaoJdbc() {
    }
    
    public TipoAsesoriaDaoJdbc(Connection conn) {
        this.conn = conn;
    }
    
    /*************************** MÉTODOS PRIVADOS *****************************/
    /**
     * Genera un objeto de transferencia de datos a partir de un ResultSet
     * @return
     * @throws SQLException 
     */
    private static TipoAsesoriaDto resultSetMapper(ResultSet rs) throws SQLException {
        TipoAsesoriaDto dto = new TipoAsesoriaDto();
        dto.setId(rs.getShort("id"));
        dto.setNombre(rs.getString("nombre"));
        dto.setDescripcion(rs.getString("descripcion"));
        dto.setHabilitado(rs.getBoolean("habilitado"));
        return dto;
    }
    
    /*************************** MÉTODOS PUBLICOS *****************************/
    @Override
    public TipoAsesoriaDto guardar(TipoAsesoriaDto tipo) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            //Sentencia SQL a ejecutar
            String sql = tipo.getId() < 0? INSERT_SQL : UPDATE_SQL;
            //Se crea el PreparedStatement
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Parámetros
            int i = 1; //Contador de parámetros
            ps.setString(i++, tipo.getNombre());
            ps.setString(i++, tipo.getDescripcion());
            ps.setBoolean(i++, tipo.isHabilitado());
            if(tipo.getId() >= 0) 
                ps.setShort(i, tipo.getId());
            //Ejecución
            if( ps.executeUpdate() == 1 ) {
                if( tipo.getId() < 0) {
                    //Se obtienen las llaves de auto-incremento generadas en un ResultSet
                    rs = ps.getGeneratedKeys();
                    //Se lee el primer registro
                    rs.first();
                    //Se obtiene el id de auto-incremento
                    tipo.setId(rs.getShort(1));
                }
            }
            else {
                tipo = null;
            }
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
    public TipoAsesoriaDto eliminar(short id) {
        TipoAsesoriaDto tipo = new TipoAsesoriaDto();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            ps = conn.prepareCall(DELETE_SQL);
            ps.setShort(1, id);
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
    public TipoAsesoriaDto obtener(short id) {
        TipoAsesoriaDto tipo = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL + " WHERE id = ?");
            ps.setShort(1, id);
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
    public List<TipoAsesoriaDto> obtenerTodos() {
        List<TipoAsesoriaDto> tipos = new ArrayList<TipoAsesoriaDto>();
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
    public List<TipoAsesoriaDto> obtenerHabilitados() {
        List<TipoAsesoriaDto> tipos = new ArrayList<TipoAsesoriaDto>();
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
