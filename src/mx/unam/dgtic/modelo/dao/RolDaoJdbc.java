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
import mx.unam.dgtic.modelo.dto.RolDto;
import mx.unam.dgtic.modelo.util.Database;

/**
 *
 * 
 */
public class RolDaoJdbc implements RolDao {
    /****************************** CONSTANTES ********************************/
    private static final String INSERT_SQL = "INSERT INTO Rol(nombre,descripcion,habilitado) VALUES (?,?,?)";
    private static final String UPDATE_SQL = "UPDATE Rol SET nombre=?, descripcion=?, habilitado=? WHERE id=?";
    private static final String DELETE_SQL = "CALL sp_DeleteRol(?)";
    private static final String SELECT_SQL = "SELECT * FROM Rol";
    
    /****************************** ATRIBUTOS *********************************/
    private static Connection userConn;

    /**************************** CONSTRUCTORES *******************************/
    public RolDaoJdbc() {
    }
    
    public RolDaoJdbc(Connection conn) {
        this.userConn = conn;
    }
    
    /*************************** MÉTODOS PRIVADOS *****************************/
    /**
     * Genera un objeto de transferencia de datos a partir de un ResultSet
     * @return
     * @throws SQLException 
     */
    private static RolDto resultSetMapper(ResultSet rs) throws SQLException {
        RolDto dto = new RolDto();
        dto.setId(rs.getShort("id"));
        dto.setNombre(rs.getString("nombre"));
        dto.setDescripcion(rs.getString("descripcion"));
        dto.setHabilitado(rs.getBoolean("habilitado"));
        return dto;
    }
    
    /*************************** MÉTODOS PÚBLICOS *****************************/
    @Override
    public RolDto guardar(RolDto rol) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = userConn != null? userConn : Database.getInstance().getConnection();
            //Sentencia SQL a ejecutar
            String sql = rol.getId() < 0? INSERT_SQL : UPDATE_SQL;
            //Se solicitan las llaves de auto-incremento generadas
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Parámetros
            int i = 1; //Contador de parámetros
            ps.setString(i++, rol.getNombre());
            ps.setString(i++, rol.getDescripcion());
            ps.setBoolean(i++, rol.isHabilitado());
            if(rol.getId() >= 0)
                ps.setShort(i, rol.getId());
            //Ejecución
            if( ps.executeUpdate() == 1 ) {
                if( rol.getId() < 0) {
                    //Se obtienen las llaves de auto-incremento generadas en un ResultSet
                    rs = ps.getGeneratedKeys();
                    //Se lee el primer registro
                    rs.first();
                    //Se obtiene el id de auto-incremento
                    rol.setId(rs.getShort(1));
                }
            } else {
                rol = null;
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(ps);
            if(userConn != conn)
                Database.getInstance().close(conn);
        }
        return rol;
    }

    @Override
    public RolDto eliminar(short id) {
        RolDto rolDto = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = userConn != null? userConn : Database.getInstance().getConnection();
            ps = conn.prepareCall(DELETE_SQL);
            ps.setShort(1, id);
            ps.execute();
            rs = ps.getResultSet();
            rs.first();
            rolDto = resultSetMapper(rs);
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(ps);
            if(conn != userConn)
                Database.getInstance().close(conn);
        }
        return rolDto;
    }

    @Override
    public List<RolDto> obtener(String username) {
        List<RolDto> roles = new ArrayList<RolDto>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = userConn != null? userConn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL + " JOIN Usuario_Rol UR ON(UR.rol = Rol.id) WHERE UR.usuario = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            rs.next();
            while(rs.next()){
                roles.add(resultSetMapper(rs));
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            if(conn != userConn)
                Database.getInstance().close(conn);
        }
        return roles;
    }
    
    @Override
    public RolDto obtener(short id) {
        RolDto rol = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = userConn != null? userConn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL + " WHERE ID = ?");
            ps.setShort(1, id);
            rs = ps.executeQuery();
            rs.next();
            rol = resultSetMapper(rs);
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            if(conn != userConn)
                Database.getInstance().close(conn);
        }
        return rol;
    }

    @Override
    public List<RolDto> obtenerTodos() {
        List<RolDto> roles = new ArrayList<RolDto>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = userConn != null? userConn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL);
            rs = ps.executeQuery();
            while(rs.next()){
                roles.add(resultSetMapper(rs));
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            if(conn != userConn)
                Database.getInstance().close(conn);
        }
        return roles;
    }

    @Override
    public List<RolDto> obtenerHabilitados() {
        List<RolDto> roles = new ArrayList<RolDto>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = userConn != null? userConn : Database.getInstance().getConnection();
            ps = userConn.prepareStatement(SELECT_SQL + " WHERE habilitado = 1");
            rs = ps.executeQuery();
            while(rs.next()){
                roles.add(resultSetMapper(rs));
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            if(conn != userConn)
                Database.getInstance().close(conn);
        }
        return roles;
    }
    
}
