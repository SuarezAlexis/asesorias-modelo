/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.dao.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.unam.dgtic.modelo.dao.ActividadDao;
import mx.unam.dgtic.modelo.dao.ActividadDaoJdbc;
import mx.unam.dgtic.modelo.dao.AsesoriaDao;
import mx.unam.dgtic.modelo.dao.AsesoriaDaoJdbc;
import mx.unam.dgtic.modelo.dao.PisoDao;
import mx.unam.dgtic.modelo.dao.PisoDaoJdbc;
import mx.unam.dgtic.modelo.dao.SolicitanteDao;
import mx.unam.dgtic.modelo.dao.SolicitanteDaoJdbc;
import mx.unam.dgtic.modelo.dao.SubtipoAsesoriaDao;
import mx.unam.dgtic.modelo.dao.SubtipoAsesoriaDaoJdbc;
import mx.unam.dgtic.modelo.dao.TipoAsesoriaDao;
import mx.unam.dgtic.modelo.dao.TipoAsesoriaDaoJdbc;
import mx.unam.dgtic.modelo.dao.TipoSolicitanteDao;
import mx.unam.dgtic.modelo.dao.TipoSolicitanteDaoJdbc;
import mx.unam.dgtic.modelo.dao.UsuarioDao;
import mx.unam.dgtic.modelo.dao.UsuarioDaoJdbc;
import mx.unam.dgtic.modelo.dto.ActividadDto;
import mx.unam.dgtic.modelo.dto.AsesoriaDto;
import mx.unam.dgtic.modelo.dto.Estado;
import mx.unam.dgtic.modelo.dto.PisoDto;
import mx.unam.dgtic.modelo.dto.SolicitanteDto;
import mx.unam.dgtic.modelo.dto.SubtipoAsesoriaDto;
import mx.unam.dgtic.modelo.dto.TipoAsesoriaDto;
import mx.unam.dgtic.modelo.dto.TipoSolicitanteDto;
import mx.unam.dgtic.modelo.dto.UsuarioDto;


/**
 *
 * @author alexis.suarez
 */
public class AsesoriaActividadDaoTest {
    public static boolean guardarAsesoria(AsesoriaDao dao, AsesoriaDto dto) {
        boolean correcto = false;
        
        try { correcto = dao.guardar(dto).equals(dto); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    public static boolean guardarActividad(ActividadDao dao, ActividadDto dto) {
        boolean correcto = false;
        
        try { correcto = dao.guardar(dto).equals(dto); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean obtenerAsesoria(AsesoriaDao dao, AsesoriaDto dto) {
        boolean correcto = false;
        
        try {  correcto = dto.equals(dao.obtener(dto.getId())); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    public static boolean obtenerActividad(ActividadDao dao, ActividadDto dto) {
        boolean correcto = false;
        
        try {  correcto = dto.equals(dao.obtener(dto.getId())); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean obtenerAsesorias(AsesoriaDao dao, List<AsesoriaDto> lista) {
        boolean correcto = false;
        try { correcto = dao.obtenerTodos().equals(lista); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    public static boolean obtenerActividades(ActividadDao dao, List<ActividadDto> lista) {
        boolean correcto = false;
        try { correcto = dao.obtenerTodos().equals(lista); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean eliminarAsesoriaDto(AsesoriaDao dao, AsesoriaDto dto) {
        boolean correcto = false;
        try { correcto = dao.eliminar(dto.getId()).equals(dto); }
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    public static boolean eliminarActividadDto(ActividadDao dao, ActividadDto dto) {
        boolean correcto = false;
        try { correcto = dao.eliminar(dto.getId()).equals(dto); }
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static void main(String[] args) {
        //Data Access Objects para todas las entidades necesarias
        AsesoriaDao asDao = new AsesoriaDaoJdbc();
        ActividadDao acDao = new ActividadDaoJdbc();
        TipoAsesoriaDao tDao = new TipoAsesoriaDaoJdbc();
        SubtipoAsesoriaDao stDao = new SubtipoAsesoriaDaoJdbc();
        PisoDao pDao = new PisoDaoJdbc();
        TipoSolicitanteDao tsDao = new TipoSolicitanteDaoJdbc();
        SolicitanteDao sDao = new SolicitanteDaoJdbc();
        UsuarioDao uDao = new UsuarioDaoJdbc();
        
        //Preparación de los objetos. Inserción en base de datos.
        TipoSolicitanteDto tipoSol = new TipoSolicitanteDto();
        tipoSol.setNombre("Profesor");
        tipoSol.setDescripcion("Docente que solicita asesoría.");
        tipoSol.setHabilitado(true);
        tsDao.guardar(tipoSol);
        
        SolicitanteDto sol = new SolicitanteDto();
        sol.setNombre("Nombre del solicitante");
        sol.setApellidos("Apellidos del solicitante");
        sol.setContacto("Contacto");
        sol.setTipo(tipoSol);
        sol.setHabilitado(true);
        sDao.guardar(sol);
        
        TipoAsesoriaDto tipoAs = new TipoAsesoriaDto();
        tipoAs.setNombre("Tipo de asesoria 1");
        tipoAs.setDescripcion("Descripción del tipo de asesoria 1");
        tipoAs.setHabilitado(true);
        tDao.guardar(tipoAs);
        
        SubtipoAsesoriaDto stAs = new SubtipoAsesoriaDto();
        stAs.setTipo(tipoAs);
        stAs.setNombre("Subtipo de asesoría 1");
        stAs.setDescripcion("Descripción del subtipo de asesoría 1");
        stAs.setHabilitado(true);
        stDao.guardar(stAs);
        
        PisoDto piso = new PisoDto();
        piso.setId((short)3);
        piso.setDescripcion("Piso 3");
        piso.setHabilitado(true);
        pDao.guardar(piso);
        
        UsuarioDto tecnico = new UsuarioDto();
        tecnico.setApellidos("Apellidos del técnico");
        tecnico.setNombre("Nombre del técnico");
        tecnico.setEmail("email@del.tecnico");
        tecnico.setHabilitado(true);
        tecnico.setUsername("TecnicoUsername");
        tecnico.setPassword("123");
        uDao.guardar(tecnico);
        
        //Pruebas de AsesoriaDao
        AsesoriaDto asesoria = new AsesoriaDto();
        asesoria.setFecha(new Date());
        asesoria.setPiso(piso);
        asesoria.setSolicitante(sol);
        asesoria.setObservaciones("bla bla bla");
        guardarAsesoria(asDao, asesoria);
        obtenerAsesoria(asDao, asesoria);
        asesoria.setObservaciones("siempre no");
        guardarAsesoria(asDao, asesoria);
        
        AsesoriaDto otraAsesoria = new AsesoriaDto();
        otraAsesoria.setFecha(new Date());
        otraAsesoria.setPiso(piso);
        otraAsesoria.setSolicitante(sol);
        otraAsesoria.setObservaciones("bli bla blu");
        guardarAsesoria(asDao, otraAsesoria);
        
        List<AsesoriaDto> todos = new ArrayList<AsesoriaDto>();
        todos.add(asesoria);
        todos.add(otraAsesoria);
        obtenerAsesorias(asDao, todos);
        
        //Pruebas de ActividadDao
        List<ActividadDto> todasLasActividades = new ArrayList<ActividadDto>();
        ActividadDto actividad = new ActividadDto();
        actividad.setAsesoria(asesoria);
        actividad.setEstado(Estado.REGISTRADO);
        actividad.setFecha(new Date());
        actividad.setObservaciones("No hay observaciones");
        actividad.setSubtipo(stAs);
        actividad.setTecnico(tecnico);
        actividad.setTipo(tipoAs);
        guardarActividad(acDao,actividad);
        obtenerActividad(acDao,actividad);
        actividad.setObservaciones("Ahora sí hay observaciones");
        actividad.setEstado(Estado.EN_PROCESO);
        guardarActividad(acDao,actividad);
        todasLasActividades.add(actividad);
        asesoria.setActividades(acDao.obtenerAsesoria(asesoria.getId()));
        
        actividad = new ActividadDto();
        actividad.setAsesoria(asesoria);
        actividad.setEstado(Estado.REGISTRADO);
        actividad.setFecha(new Date());
        actividad.setObservaciones("Nueva actividad");
        actividad.setSubtipo(stAs);
        actividad.setTecnico(tecnico);
        actividad.setTipo(tipoAs);
        guardarActividad(acDao,actividad);
        asesoria.setActividades(acDao.obtenerAsesoria(asesoria.getId()));
        todasLasActividades.add(actividad);
        
        actividad = new ActividadDto();
        actividad.setAsesoria(otraAsesoria);
        actividad.setEstado(Estado.REGISTRADO);
        actividad.setFecha(new Date());
        actividad.setObservaciones("Nueva actividad");
        actividad.setSubtipo(stAs);
        actividad.setTecnico(tecnico);
        actividad.setTipo(tipoAs);
        guardarActividad(acDao,actividad);
        todasLasActividades.add(actividad);
        obtenerActividades(acDao,todasLasActividades);
        
        eliminarActividadDto(acDao, todasLasActividades.get(0));
        eliminarAsesoriaDto(asDao, asesoria);
        eliminarAsesoriaDto(asDao, otraAsesoria);
        
        uDao.eliminar(tecnico.getUsername());
        stDao.eliminar(stAs.getId());
        tDao.eliminar(tipoAs.getId());
        sDao.eliminar(sol.getId());
        tsDao.eliminar(tipoSol.getNombre());
        pDao.eliminar(piso.getId());   
    }
}
