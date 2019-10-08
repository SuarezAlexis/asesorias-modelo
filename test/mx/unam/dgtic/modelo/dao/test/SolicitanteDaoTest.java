/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.dao.test;

import java.util.ArrayList;
import java.util.List;
import mx.unam.dgtic.modelo.dao.SolicitanteDao;
import mx.unam.dgtic.modelo.dao.SolicitanteDaoJdbc;
import mx.unam.dgtic.modelo.dao.TipoSolicitanteDao;
import mx.unam.dgtic.modelo.dao.TipoSolicitanteDaoJdbc;
import mx.unam.dgtic.modelo.dto.SolicitanteDto;
import mx.unam.dgtic.modelo.dto.TipoSolicitanteDto;

/**
 *
 * @author alexis.suarez
 */
public class SolicitanteDaoTest {
    public static boolean guardarSolicitante(SolicitanteDao dao, SolicitanteDto dto) {
        boolean correcto = false;
        
        try { correcto = dao.guardar(dto).equals(dto); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean obtenerSolicitante(SolicitanteDao dao, SolicitanteDto dto) {
        boolean correcto = false;
        
        try {  correcto = dto.equals(dao.obtener(dto.getId())); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean obtenerHabilitados(SolicitanteDao dao, List<SolicitanteDto> lista) {
        boolean correcto = false;
        try { correcto = dao.obtenerHabilitados().equals(lista); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean obtenerTodos(SolicitanteDao dao, List<SolicitanteDto> lista) {
        boolean correcto = false;
        try { correcto = dao.obtenerTodos().equals(lista); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean eliminarSolicitanteDto(SolicitanteDao dao, SolicitanteDto dto) {
        boolean correcto = false;
        try { correcto = dao.eliminar(dto.getId()).equals(dto); }
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static void main(String[] args) {
        SolicitanteDao dao = new SolicitanteDaoJdbc();
        TipoSolicitanteDao tDao = new TipoSolicitanteDaoJdbc();
        
        TipoSolicitanteDto tipo = new TipoSolicitanteDto();
        tipo.setNombre("Profesor");
        tipo.setDescripcion("Docente que solicita una asesoría.");
        tipo.setHabilitado(true);
        tDao.guardar(tipo);
        
        SolicitanteDto sol = new SolicitanteDto();
        sol.setNombre("Pedro");
        sol.setApellidos("Páramo");
        sol.setTipo(tipo);
        sol.setContacto("5566778899");
        sol.setHabilitado(true);
        guardarSolicitante(dao, sol);
        obtenerSolicitante(dao, sol);
        sol.setContacto("nuevo.contacto@dgenp.unam.mx");
        guardarSolicitante(dao, sol);
        
        SolicitanteDto sol2 = new SolicitanteDto();
        sol2.setNombre("Aurelio");
        sol2.setApellidos("Buendía");
        sol2.setContacto("123456");
        sol2.setTipo(tipo);
        sol2.setHabilitado(false);
        guardarSolicitante(dao, sol2);
        
        List<SolicitanteDto> habilitados = new ArrayList<SolicitanteDto>();
        habilitados.add(sol);
        obtenerHabilitados(dao, habilitados);
        
        List<SolicitanteDto> todos = new ArrayList<SolicitanteDto>();
        todos.add(sol);
        todos.add(sol2);
        obtenerTodos(dao, todos);
        
        eliminarSolicitanteDto(dao, sol);
        eliminarSolicitanteDto(dao, sol2);
        tDao.eliminar(tipo.getNombre());
    }
}
