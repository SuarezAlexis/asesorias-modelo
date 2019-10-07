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
import mx.unam.dgtic.modelo.dto.PisoDto;
import mx.unam.dgtic.modelo.util.Database;

/**
 *
 * @author alexis.suarez
 */
public class PisoDaoJdbc implements PisoDao {
    /****************************** CONSTANTES ********************************/
    private static final String INSERT_SQL = "INSERT INTO Piso(id,descripcion,habilitado) VALUES (?,?,?) ON DUPLICATE KEY UPDATE descripcion = ?, habilitado = ?";
    private static final String DELETE_SQL = "CALL sp_DeletePiso(?)";
    private static final String SELECT_SQL = "SELECT * FROM Piso";
    
    /****************************** ATRIBUTOS *********************************/
    private static Connection conn;

    /**************************** CONSTRUCTORES *******************************/
    public PisoDaoJdbc() {
    }
    
    public PisoDaoJdbc(Connection conn) {
        this.conn = conn;
    }
    
    /*************************** MÉTODOS PRIVADOS *****************************/
    /**
     * Genera un objeto de transferencia de datos a partir de un ResultSet
     * @return
     * @throws SQLException 
     */
    private static PisoDto resultSetMapper(ResultSet rs) throws SQLException {
        PisoDto dto = new PisoDto();
        dto.setId(rs.getShort("id"));
        dto.setDescripcion(rs.getString("descripcion"));
        dto.setHabilitado(rs.getBoolean("habilitado"));
        return dto;
    }
    
    /*************************** MÉTODOS PUBLICOS *****************************/
    @Override
    public PisoDto guardar(PisoDto piso) {
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
            ps.setShort(i++, piso.getId());
            ps.setString(i++, piso.getDescripcion());
            ps.setBoolean(i++, piso.isHabilitado());
            ps.setString(i++, piso.getDescripcion());
            ps.setBoolean(i++, piso.isHabilitado());

            //Ejecución
            if( ps.executeUpdate() < 1 )
                piso = null;
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(ps);
            if(this.conn != conn)
                Database.getInstance().close(conn);
        }
        return piso;
    }

    @Override
    public PisoDto eliminar(short id) {
        PisoDto piso = new PisoDto();
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
            piso = resultSetMapper(rs);
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(ps);
            if(conn != this.conn)
                Database.getInstance().close(conn);
        }
        return piso;
    }

    @Override
    public PisoDto obtener(short id) {
        PisoDto piso = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL + " WHERE id = ?");
            ps.setShort(1, id);
            rs = ps.executeQuery();
            rs.next();
            piso = resultSetMapper(rs);
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            if(conn != this.conn)
                Database.getInstance().close(conn);
        }
        return piso;
    }

    @Override
    public List<PisoDto> obtenerTodos() {
        List<PisoDto> pisos = new ArrayList<PisoDto>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL);
            rs = ps.executeQuery();
            while(rs.next()){
                pisos.add(resultSetMapper(rs));
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            if(conn != this.conn)
                Database.getInstance().close(conn);
        }
        return pisos;
    }

    @Override
    public List<PisoDto> obtenerHabilitados() {
        List<PisoDto> pisos = new ArrayList<PisoDto>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL + " WHERE habilitado = 1");
            rs = ps.executeQuery();
            while(rs.next()){
                pisos.add(resultSetMapper(rs));
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            if(conn != this.conn)
                Database.getInstance().close(conn);
        }
        return pisos;
    }
    
}
