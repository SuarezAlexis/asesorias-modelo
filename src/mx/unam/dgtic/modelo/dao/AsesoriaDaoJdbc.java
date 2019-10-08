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
import mx.unam.dgtic.modelo.dto.AsesoriaDto;
import mx.unam.dgtic.modelo.dto.PisoDto;
import mx.unam.dgtic.modelo.dto.SolicitanteDto;
import mx.unam.dgtic.modelo.dto.TipoSolicitanteDto;
import mx.unam.dgtic.modelo.util.Database;

/**
 *
 * @author alexis.suarez
 */
public class AsesoriaDaoJdbc implements AsesoriaDao {

    /****************************** CONSTANTES ********************************/
    private static final String INSERT_SQL = "INSERT INTO Asesoria(fecha,solicitante,piso,observaciones) VALUES (?,?,?,?)";
    private static final String UPDATE_SQL = "UPDATE Asesoria SET fecha = ?, solicitante = ?, piso = ?, observaciones = ? WHERE id = ?";
    private static final String DELETE_SQL = "CALL sp_DeleteAsesoria(?)";
    private static final String SELECT_SQL = "SELECT A.*, S.nombre solicitante_nombre, S.apellidos solicitante_apellidos, S.tipo solicitante_tipo, T.descripcion tipo_descripcion, T.habilitado tipo_habilitado, S.contacto solicitante_contacto, S.habilitado solicitante_habilitado, P.descripcion piso_descripcion, P.habilitado piso_habilitado FROM Asesoria A JOIN Solicitante S ON(S.id = A.solicitante) JOIN TipoSolicitante T ON(T.nombre = S.tipo) JOIN Piso P ON(P.id = A.piso)";
    private static final String SELECT_ACTIVIDADES_SQL = "SELECT A.*, U.*,S.tipo, S.nombre subtipo_nombre, S.descripcion subtipo_descripcion, S.habilitado subtipo_habilitado, T.nombre tipo_nombre, T.descripcion tipo_descripcion, T.habilitado tipo_habilitado FROM Actividad A JOIN SubtipoAsesoria S ON(S.id = A.subtipo) JOIN TipoAsesoria T ON(T.id = S.tipo) LEFT JOIN Usuario U ON(U.username = A.tecnico)";
    
    /****************************** ATRIBUTOS *********************************/
    private static Connection conn;

    /**************************** CONSTRUCTORES *******************************/
    public AsesoriaDaoJdbc() {
    }
    
    public AsesoriaDaoJdbc(Connection conn) {
        this.conn = conn;
    }
    
    /*************************** MÉTODOS PRIVADOS *****************************/
    /**
     * Genera un objeto de transferencia de datos a partir de un ResultSet
     * @return
     * @throws SQLException 
     */
    private static AsesoriaDto resultSetMapper(ResultSet rs) throws SQLException {
        AsesoriaDto dto = new AsesoriaDto();
        dto.setId(rs.getShort("id"));
        dto.setFecha(rs.getTimestamp("fecha"));
        dto.setSolicitante(new SolicitanteDto());
        dto.getSolicitante().setId(rs.getInt("solicitante"));
        dto.getSolicitante().setNombre(rs.getString("solicitante_nombre"));
        dto.getSolicitante().setApellidos(rs.getString("solicitante_apellidos"));
        dto.getSolicitante().setTipo(new TipoSolicitanteDto());
        dto.getSolicitante().getTipo().setNombre(rs.getString("solicitante_tipo"));
        dto.getSolicitante().getTipo().setDescripcion(rs.getString("tipo_descripcion"));
        dto.getSolicitante().getTipo().setHabilitado(rs.getBoolean("tipo_habilitado"));
        dto.getSolicitante().setContacto(rs.getString("solicitante_contacto"));
        dto.getSolicitante().setHabilitado(rs.getBoolean("solicitante_habilitado"));
        dto.setPiso(new PisoDto());
        dto.getPiso().setId(rs.getShort("piso"));
        dto.getPiso().setDescripcion(rs.getString("piso_descripcion"));
        dto.getPiso().setHabilitado(rs.getBoolean("piso_habilitado"));
        dto.setObservaciones(rs.getString("observaciones"));
        return dto;
    }
    
    /*************************** MÉTODOS PUBLICOS *****************************/
    @Override
    public AsesoriaDto guardar(AsesoriaDto asesoria) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            //Sentencia SQL a ejecutar
            String sql = asesoria.getId() < 0? INSERT_SQL : UPDATE_SQL;
            //Se crea el PreparedStatement
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Parámetros
            int i = 1; //Contador de parámetros
            ps.setTimestamp(i++, new Timestamp(asesoria.getFecha().getTime()));
            ps.setInt(i++, asesoria.getSolicitante().getId());
            ps.setShort(i++, asesoria.getPiso().getId());
            ps.setString(i++, asesoria.getObservaciones());
            if(asesoria.getId() >= 0) 
                ps.setLong(i, asesoria.getId());
            //Ejecución
            if( ps.executeUpdate() == 1 ) {
                if( asesoria.getId() < 0) {
                    //Se obtienen las llaves de auto-incremento generadas en un ResultSet
                    rs = ps.getGeneratedKeys();
                    //Se lee el primer registro
                    rs.first();
                    //Se obtiene el id de auto-incremento
                    asesoria.setId(rs.getInt(1));
                }
            }
            else {
                asesoria = null;
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(ps);
            if(this.conn != conn)
                Database.getInstance().close(conn);
        }
        return asesoria;
    }

    @Override
    public AsesoriaDto eliminar(long id) {
        AsesoriaDto asesoria = new AsesoriaDto();
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
            asesoria = resultSetMapper(rs);
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(ps);
            if(conn != this.conn)
                Database.getInstance().close(conn);
        }
        return asesoria;
    }

    @Override
    public AsesoriaDto obtener(long id) {
        AsesoriaDto asesoria = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL + " WHERE A.id = ?");
            ps.setLong(1, id);
            rs = ps.executeQuery();
            rs.next();
            asesoria = resultSetMapper(rs);
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            if(conn != this.conn)
                Database.getInstance().close(conn);
        }
        return asesoria;
    }

    @Override
    public List<AsesoriaDto> obtenerTodos() {
        List<AsesoriaDto> asesorias = new ArrayList<AsesoriaDto>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = this.conn != null? this.conn : Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL);
            rs = ps.executeQuery();
            while(rs.next()){
                asesorias.add(resultSetMapper(rs));
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            if(conn != this.conn)
                Database.getInstance().close(conn);
        }
        return asesorias;
    }
}
