/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.dao.test;

import java.util.ArrayList;
import java.util.List;
import mx.unam.dgtic.modelo.dao.TipoSolicitanteDao;
import mx.unam.dgtic.modelo.dao.TipoSolicitanteDaoJdbc;
import mx.unam.dgtic.modelo.dto.TipoSolicitanteDto;

/**
 *
 * @author alexis.suarez
 */
public class TipoSolicitanteDaoTest {
    
    public static boolean guardarTipoSolicitante(TipoSolicitanteDao dao, TipoSolicitanteDto dto) {
        boolean correcto = false;
        
        try { correcto = dao.guardar(dto).equals(dto); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean obtenerTipoSolicitante(TipoSolicitanteDao dao, TipoSolicitanteDto dto) {
        boolean correcto = false;
        
        try {  correcto = dto.equals(dao.obtener(dto.getNombre())); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean obtenerHabilitados(TipoSolicitanteDao dao, List<TipoSolicitanteDto> lista) {
        boolean correcto = false;
        try { correcto = dao.obtenerHabilitados().equals(lista); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean obtenerTodos(TipoSolicitanteDao dao, List<TipoSolicitanteDto> lista) {
        boolean correcto = false;
        try { correcto = dao.obtenerTodos().equals(lista); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean eliminarTipoSolicitanteDto(TipoSolicitanteDao dao, TipoSolicitanteDto dto) {
        boolean correcto = false;
        try { correcto = dao.eliminar(dto.getNombre()).equals(dto); }
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static void main(String[] args) {
        TipoSolicitanteDao dao = new TipoSolicitanteDaoJdbc();
        
        TipoSolicitanteDto prof = new TipoSolicitanteDto();
        prof.setNombre("Profesor");
        prof.setDescripcion("Docente que solicita asesoría de cómputo");
        prof.setHabilitado(true);
        guardarTipoSolicitante(dao, prof);
        obtenerTipoSolicitante(dao, prof);
        prof.setDescripcion("Nueva descripcion");
        guardarTipoSolicitante(dao, prof);
        
        TipoSolicitanteDto trab = new TipoSolicitanteDto();
        trab.setNombre("Trabajador");
        trab.setDescripcion("Trabajador que solicita asesoría de cómputo");
        trab.setHabilitado(false);
        guardarTipoSolicitante(dao, trab);
        
        List<TipoSolicitanteDto> habilitados = new ArrayList<TipoSolicitanteDto>();
        habilitados.add(prof);
        obtenerHabilitados(dao, habilitados);
        
        List<TipoSolicitanteDto> todos = new ArrayList<TipoSolicitanteDto>();
        todos.add(prof);
        todos.add(trab);
        obtenerTodos(dao, todos);
        
        eliminarTipoSolicitanteDto(dao, prof);
        eliminarTipoSolicitanteDto(dao, trab);
        
    }
}
