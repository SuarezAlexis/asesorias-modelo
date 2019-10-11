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
import mx.unam.dgtic.modelo.dto.RolDto;
import mx.unam.dgtic.modelo.dto.UsuarioDto;
import mx.unam.dgtic.modelo.util.Database;

/**
 *
 * 
 */
public class UsuarioDaoJdbc implements UsuarioDao {
    /****************************** CONSTANTES ********************************/
    private static final String UPSERT_SQL = "CALL sp_UpsertUsuario(?,?,?,?,?,?)";
    private static final String DELETE_SQL = "CALL sp_DeleteUsuario(?)";
    private static final String SELECT_SQL = "SELECT U.*, R.* FROM Usuario U LEFT JOIN Usuario_Rol UR ON(UR.usuario = U.username) LEFT JOIN Rol R ON(R.id = UR.rol)";
    private static final String UPDATE_ROLES_SQL = "CALL sp_UpdateUsuarioRoles(?,?)";
    
    /****************************** ATRIBUTOS *********************************/
    private static Connection userConn;
    
    /**************************** CONSTRUCTORES *******************************/
    public UsuarioDaoJdbc() {}
    
    public UsuarioDaoJdbc(Connection conn) {
        this.userConn = conn;
    }
    
    /*************************** MÉTODOS PRIVADOS *****************************/
    /**
     * Genera un objeto de transferencia de datos a partir de un ResultSet
     * @return
     * @throws SQLException 
     */
    private static UsuarioDto resultSetMapper(ResultSet rs) throws SQLException {
        UsuarioDto dto = new UsuarioDto();
        dto.setUsername(rs.getString("username"));
        dto.setNombre(rs.getString("nombre"));
        dto.setApellidos(rs.getString("apellidos"));
        dto.setEmail(rs.getString("email"));
        dto.setPassword(rs.getString("password"));
        dto.setHabilitado(rs.getBoolean("habilitado"));
        return dto;
    }
    
    /**
     * Genera un objeto de transferencia de datos a partir de un ResultSet
     * @return
     * @throws SQLException 
     */
    private static RolDto rolResultSetMapper(ResultSet rs) throws SQLException {
        RolDto dto = new RolDto();
        dto.setId(rs.getShort("id"));
        dto.setNombre(rs.getString("nombre"));
        dto.setDescripcion(rs.getString("descripcion"));
        dto.setHabilitado(rs.getBoolean("habilitado"));
        return dto;
    }
    
    @Override
    public UsuarioDto guardar(UsuarioDto u) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String roles = "";
        for(RolDto r : u.getRoles()) {
            roles += r.getId() + ",";
        } roles = roles.substring(0,Math.max(0,roles.length()-1));
        try {
            conn = userConn != null? userConn : Database.getInstance().getConnection();
            ps = conn.prepareCall(UPSERT_SQL);
            
            int i = 1;
            ps.setString(i++, u.getUsername());
            ps.setString(i++, u.getNombre());
            ps.setString(i++, u.getApellidos());
            ps.setString(i++, u.getPassword());
            ps.setString(i++, u.getEmail());
            ps.setBoolean(i, u.isHabilitado());
            
            ps.execute();
            rs = ps.getResultSet();
            rs.first();
            u = resultSetMapper(rs);
            
            ps = conn.prepareCall(UPDATE_ROLES_SQL);
            i = 1;
            ps.setString(i++, u.getUsername());
            ps.setString(i, roles);
            ps.execute();
            rs = ps.getResultSet();
            u.setRoles(new ArrayList<RolDto>());
            while(rs.next()) {
                u.getRoles().add(rolResultSetMapper(rs));
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(ps);
            if(conn != userConn)
                Database.getInstance().close(conn);
        }
        return u;
    }

    @Override
    public UsuarioDto eliminar(String username) {
        UsuarioDto u = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = userConn != null? userConn : Database.getInstance().getConnection();
            ps = conn.prepareCall(DELETE_SQL);
            ps.setString(1, username);
            ps.execute();
            rs = ps.getResultSet();
            rs.first();
            u = resultSetMapper(rs);
            u.setRoles(new ArrayList<RolDto>());
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(ps);
            if(conn != userConn)
                Database.getInstance().close(conn);
        }
        return u;
    }

    @Override
    public UsuarioDto autenticar(String username, String password) {
        UsuarioDto u = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = userConn != null? userConn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL + " WHERE username = ? AND password = ? AND U.habilitado = 1");
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            rs.next();
            u = resultSetMapper(rs);
            do {
                u.getRoles().add(rolResultSetMapper(rs));
            } while(rs.next());
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            if(conn != userConn)
                Database.getInstance().close(conn);
        }
        return u;
    }

    @Override
    public UsuarioDto obtener(String username) {
        UsuarioDto u = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = userConn != null? userConn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL + " WHERE username = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            rs.next();
            u = resultSetMapper(rs);
            do {
                u.getRoles().add(rolResultSetMapper(rs));
            } while(rs.next());
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            if(conn != userConn)
                Database.getInstance().close(conn);
        }
        return u;
    }

    @Override
    public List<UsuarioDto> obtenerTodos() {
        List<UsuarioDto> usuarios = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = userConn != null? userConn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL);
            rs = ps.executeQuery();
            while(rs.next()) {
                UsuarioDto dto = resultSetMapper(rs);
                dto.setRoles(new ArrayList<RolDto>());
                if(!usuarios.contains(dto))
                    usuarios.add(dto);
                RolDto rolDto = rolResultSetMapper(rs);
                rolDto.setNombre(rs.getString(8));
                rolDto.setHabilitado(rs.getBoolean(10));
                usuarios.get(usuarios.size() - 1).getRoles().add(rolDto);
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            if(conn != userConn)
                Database.getInstance().close(conn);
        }
        return usuarios;
    }
    
}
