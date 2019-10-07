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
import mx.unam.dgtic.modelo.dto.SubtipoAsesoriaDto;
import mx.unam.dgtic.modelo.dto.TipoAsesoriaDto;
import mx.unam.dgtic.modelo.util.Database;

/**
 *
 * @author alexis.suarez
 */
public class SubtipoAsesoriaDaoJdbc implements SubtipoAsesoriaDao {

    /****************************** CONSTANTES ********************************/
    private static final String INSERT_SQL = "INSERT INTO SubtipoAsesoria(nombre,tipo,descripcion,habilitado) VALUES (?,?,?,?)";
    private static final String UPDATE_SQL = "UPDATE SubtipoAsesoria SET nombre = ?, tipo = ?, descripcion = ?, habilitado = ? WHERE id = ?";
    private static final String DELETE_SQL = "CALL sp_DeleteSubtipoAsesoria(?)";
    private static final String SELECT_SQL = "SELECT S.*,T.nombre tipo_nombre, T.descripcion tipo_descripcion, T.habilitado tipo_habilitado FROM SubtipoAsesoria S JOIN TipoAsesoria T ON(T.id = S.tipo)";
    
    /****************************** ATRIBUTOS *********************************/
    private static Connection conn;

    /**************************** CONSTRUCTORES *******************************/
    public SubtipoAsesoriaDaoJdbc() {
    }
    
    public SubtipoAsesoriaDaoJdbc(Connection conn) {
        this.conn = conn;
    }
    
    /*************************** MÉTODOS PRIVADOS *****************************/
    /**
     * Genera un objeto de transferencia de datos a partir de un ResultSet
     * @return
     * @throws SQLException 
     */
    private static SubtipoAsesoriaDto resultSetMapper(ResultSet rs) throws SQLException {
        SubtipoAsesoriaDto dto = new SubtipoAsesoriaDto();
        dto.setId(rs.getShort("id"));
        dto.setTipo(new TipoAsesoriaDto());
        dto.getTipo().setId(rs.getShort("tipo"));
        dto.getTipo().setNombre(rs.getString("tipo_nombre"));
        dto.getTipo().setDescripcion(rs.getString("tipo_descripcion"));
        dto.getTipo().setHabilitado(rs.getBoolean("tipo_habilitado"));
        dto.setNombre(rs.getString("nombre"));
        dto.setDescripcion(rs.getString("descripcion"));
        dto.setHabilitado(rs.getBoolean("habilitado"));
        return dto;
    }
    
    /*************************** MÉTODOS PUBLICOS *****************************/
    @Override
    public SubtipoAsesoriaDto guardar(SubtipoAsesoriaDto subtipo) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            //Sentencia SQL a ejecutar
            String sql = subtipo.getId() < 0? INSERT_SQL : UPDATE_SQL;
            //Se crea el PreparedStatement
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Parámetros
            int i = 1; //Contador de parámetros
            ps.setString(i++, subtipo.getNombre());
            ps.setShort(i++, subtipo.getTipo().getId());
            ps.setString(i++, subtipo.getDescripcion());
            ps.setBoolean(i++, subtipo.isHabilitado());
            if(subtipo.getId() >= 0) 
                ps.setShort(i, subtipo.getId());
            //Ejecución
            if( ps.executeUpdate() == 1 ) {
                if( subtipo.getId() < 0) {
                    //Se obtienen las llaves de auto-incremento generadas en un ResultSet
                    rs = ps.getGeneratedKeys();
                    //Se lee el primer registro
                    rs.first();
                    //Se obtiene el id de auto-incremento
                    subtipo.setId(rs.getShort(1));
                }
            }
            else {
                subtipo = null;
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(ps);
            if(this.conn != conn)
                Database.getInstance().close(conn);
        }
        return subtipo;
    }

    @Override
    public SubtipoAsesoriaDto eliminar(short id) {
        SubtipoAsesoriaDto tipo = new SubtipoAsesoriaDto();
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
    public SubtipoAsesoriaDto obtener(short id) {
        SubtipoAsesoriaDto tipo = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL + " WHERE S.id = ?");
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
    public List<SubtipoAsesoriaDto> obtenerTodos() {
        List<SubtipoAsesoriaDto> tipos = new ArrayList<SubtipoAsesoriaDto>();
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
    public List<SubtipoAsesoriaDto> obtenerHabilitados() {
        List<SubtipoAsesoriaDto> tipos = new ArrayList<SubtipoAsesoriaDto>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL + " WHERE S.habilitado = 1");
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
