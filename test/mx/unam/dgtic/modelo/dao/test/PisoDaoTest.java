/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.dao.test;

import java.util.ArrayList;
import java.util.List;
import mx.unam.dgtic.modelo.dao.PisoDao;
import mx.unam.dgtic.modelo.dao.PisoDaoJdbc;
import mx.unam.dgtic.modelo.dto.PisoDto;

/**
 *
 * @author alexis.suarez
 */
public class PisoDaoTest {
    public static boolean guardarPiso(PisoDao dao, PisoDto dto) {
        boolean correcto = false;
        
        try { correcto = dao.guardar(dto).equals(dto); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean obtenerPiso(PisoDao dao, PisoDto dto) {
        boolean correcto = false;
        
        try {  correcto = dto.equals(dao.obtener(dto.getId())); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean obtenerHabilitados(PisoDao dao, List<PisoDto> lista) {
        boolean correcto = false;
        try { correcto = dao.obtenerHabilitados().equals(lista); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean obtenerTodos(PisoDao dao, List<PisoDto> lista) {
        boolean correcto = false;
        try { correcto = dao.obtenerTodos().equals(lista); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean eliminarPisoDto(PisoDao dao, PisoDto dto) {
        boolean correcto = false;
        try { correcto = dao.eliminar(dto.getId()).equals(dto); }
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static void main(String[] args) {
        PisoDao dao = new PisoDaoJdbc();
        
        PisoDto uno = new PisoDto();
        uno.setId((short)1);
        uno.setDescripcion("Piso 1");
        uno.setHabilitado(true);
        guardarPiso(dao, uno);
        obtenerPiso(dao, uno);
        uno.setDescripcion("Nueva descripción");
        guardarPiso(dao, uno);
        
        PisoDto tres = new PisoDto();
        tres.setId((short)3);
        tres.setDescripcion("Piso 3");
        tres.setHabilitado(false);
        guardarPiso(dao, tres);
        
        List<PisoDto> habilitados = new ArrayList<PisoDto>();
        habilitados.add(uno);
        obtenerHabilitados(dao, habilitados);
        
        List<PisoDto> todos = new ArrayList<PisoDto>();
        todos.add(uno);
        todos.add(tres);
        obtenerTodos(dao, todos);
        
        eliminarPisoDto(dao, uno);
        eliminarPisoDto(dao, tres);
        
    }
}
