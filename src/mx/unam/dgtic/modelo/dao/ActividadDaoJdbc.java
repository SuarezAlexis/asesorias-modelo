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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import mx.unam.dgtic.modelo.dto.ActividadDto;
import mx.unam.dgtic.modelo.dto.AsesoriaDto;
import mx.unam.dgtic.modelo.dto.Estado;
import mx.unam.dgtic.modelo.dto.SubtipoAsesoriaDto;
import mx.unam.dgtic.modelo.dto.TipoAsesoriaDto;
import mx.unam.dgtic.modelo.dto.UsuarioDto;
import mx.unam.dgtic.modelo.util.Database;

/**
 *
 * @author alexis.suarez
 */
public class ActividadDaoJdbc implements ActividadDao {
    
    /****************************** CONSTANTES ********************************/
    private static final String INSERT_SQL = "INSERT INTO Actividad(asesoria,fecha,estado,tecnico,subtipo,observaciones) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_SQL = "UPDATE Actividad SET fecha = ?, estado = ?, tecnico = ?, subtipo = ?, observaciones = ? WHERE id = ?";
    private static final String DELETE_SQL = "CALL sp_DeleteActividad(?)";
    private static final String SELECT_SQL = "SELECT A.*, U.*,S.tipo, S.nombre subtipo_nombre, S.descripcion subtipo_descripcion, S.habilitado subtipo_habilitado, T.nombre tipo_nombre, T.descripcion tipo_descripcion, T.habilitado tipo_habilitado FROM Actividad A JOIN SubtipoAsesoria S ON(S.id = A.subtipo) JOIN TipoAsesoria T ON(T.id = S.tipo) LEFT JOIN Usuario U ON(U.username = A.tecnico)";
    
    /****************************** ATRIBUTOS *********************************/
    private static Connection conn;

    /**************************** CONSTRUCTORES *******************************/
    public ActividadDaoJdbc() {
    }
    
    public ActividadDaoJdbc(Connection conn) {
        this.conn = conn;
    }
    
    /*************************** MÉTODOS PRIVADOS *****************************/
    /**
     * Genera un objeto de transferencia de datos a partir de un ResultSet
     * @return
     * @throws SQLException 
     */
    private static ActividadDto resultSetMapper(ResultSet rs) throws SQLException {
        ActividadDto dto = new ActividadDto();
        dto.setId(rs.getShort("id"));
        dto.setAsesoria(new AsesoriaDto());
        dto.getAsesoria().setId(rs.getLong("asesoria"));
        dto.setFecha(rs.getTimestamp("fecha"));
        dto.setEstado(Estado.valueOf(rs.getString("estado")));
        dto.setTecnico(new UsuarioDto());
        dto.getTecnico().setUsername(rs.getString("tecnico"));
        dto.getTecnico().setNombre(rs.getString("nombre"));
        dto.getTecnico().setApellidos(rs.getString("apellidos"));
        dto.getTecnico().setEmail(rs.getString("email"));
        dto.getTecnico().setHabilitado(rs.getBoolean("habilitado"));
        dto.setSubtipo(new SubtipoAsesoriaDto());
        dto.getSubtipo().setId(rs.getShort("subtipo"));
        dto.getSubtipo().setTipo(new TipoAsesoriaDto());
        dto.getSubtipo().getTipo().setId(rs.getShort("tipo"));
        dto.getSubtipo().getTipo().setNombre(rs.getString("tipo_nombre"));
        dto.getSubtipo().getTipo().setDescripcion(rs.getString("tipo_descripcion"));
        dto.getSubtipo().getTipo().setHabilitado(rs.getBoolean("tipo_habilitado"));
        dto.getSubtipo().setNombre(rs.getString("subtipo_nombre"));
        dto.getSubtipo().setDescripcion(rs.getString("subtipo_descripcion"));
        dto.getSubtipo().setHabilitado(rs.getBoolean("subtipo_habilitado"));
        dto.setObservaciones(rs.getString("observaciones"));
        return dto;
    }
    
    /*************************** MÉTODOS PUBLICOS *****************************/
    @Override
    public ActividadDto guardar(ActividadDto actividad) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            //Sentencia SQL a ejecutar
            String sql = actividad.getId() < 0? INSERT_SQL : UPDATE_SQL;
            //Se crea el PreparedStatement
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Parámetros
            int i = 1; //Contador de parámetros
            if(actividad.getId() < 0)
                ps.setLong(i++, actividad.getAsesoria().getId());
            ps.setTimestamp(i++, new Timestamp(actividad.getFecha().getTime()));
            ps.setString(i++, actividad.getEstado().name());
            ps.setString(i++, actividad.getTecnico().getUsername());
            ps.setShort(i++, actividad.getSubtipo().getId());
            ps.setString(i++, actividad.getObservaciones());
            if(actividad.getId() >= 0) 
                ps.setLong(i, actividad.getId());
            //Ejecución
            if( ps.executeUpdate() == 1 ) {
                if( actividad.getId() < 0) {
                    //Se obtienen las llaves de auto-incremento generadas en un ResultSet
                    rs = ps.getGeneratedKeys();
                    //Se lee el primer registro
                    rs.first();
                    //Se obtiene el id de auto-incremento
                    actividad.setId(rs.getLong(1));
                }
            }
            else {
                actividad = null;
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(ps);
            if(this.conn != conn)
                Database.getInstance().close(conn);
        }
        return actividad;
    }

    @Override
    public ActividadDto eliminar(long id) {
        ActividadDto actividad = new ActividadDto();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            ps = conn.prepareCall(DELETE_SQL);
            ps.setLong(1, id);
            ps.execute();
            rs = ps.getResultSet();
            rs.first();
            actividad = resultSetMapper(rs);
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(ps);
            if(conn != this.conn)
                Database.getInstance().close(conn);
        }
        return actividad;
    }

    @Override
    public ActividadDto obtener(long id) {
        ActividadDto actividad = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL + " WHERE A.id = ?");
            ps.setLong(1, id);
            rs = ps.executeQuery();
            rs.next();
            actividad = resultSetMapper(rs);
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            if(conn != this.conn)
                Database.getInstance().close(conn);
        }
        return actividad;
    }
    
    @Override
    public List<ActividadDto> obtenerAsesoria(long id) {
        List<ActividadDto> actividades = new ArrayList<ActividadDto>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL + " WHERE A.asesoria = ?");
            ps.setLong(1, id);
            rs = ps.executeQuery();
            while(rs.next()){
                actividades.add(resultSetMapper(rs));
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            if(conn != this.conn)
                Database.getInstance().close(conn);
        }
        return actividades;
    }

    @Override
    public List<ActividadDto> obtenerTodos() {
        List<ActividadDto> actividades = new ArrayList<ActividadDto>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL);
            rs = ps.executeQuery();
            while(rs.next()){
                actividades.add(resultSetMapper(rs));
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            if(conn != this.conn)
                Database.getInstance().close(conn);
        }
        return actividades;
    }
}
