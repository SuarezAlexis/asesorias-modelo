/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.dao.test;

import java.util.ArrayList;
import java.util.List;
import mx.unam.dgtic.modelo.dao.SubtipoAsesoriaDao;
import mx.unam.dgtic.modelo.dao.SubtipoAsesoriaDaoJdbc;
import mx.unam.dgtic.modelo.dao.TipoAsesoriaDao;
import mx.unam.dgtic.modelo.dao.TipoAsesoriaDaoJdbc;
import mx.unam.dgtic.modelo.dto.SubtipoAsesoriaDto;
import mx.unam.dgtic.modelo.dto.TipoAsesoriaDto;

/**
 *
 * @author alexis.suarez
 */
public class SubtipoAsesoriaDaoTest {
    public static boolean guardarSubtipoAsesoria(SubtipoAsesoriaDao dao, SubtipoAsesoriaDto dto) {
        boolean correcto = false;
        
        try { correcto = dao.guardar(dto).equals(dto); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean obtenerSubtipoAsesoria(SubtipoAsesoriaDao dao, SubtipoAsesoriaDto dto) {
        boolean correcto = false;
        
        try {  correcto = dto.equals(dao.obtener(dto.getId())); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean obtenerHabilitados(SubtipoAsesoriaDao dao, List<SubtipoAsesoriaDto> lista) {
        boolean correcto = false;
        try { correcto = dao.obtenerHabilitados().equals(lista); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean obtenerTodos(SubtipoAsesoriaDao dao, List<SubtipoAsesoriaDto> lista) {
        boolean correcto = false;
        try { correcto = dao.obtenerTodos().equals(lista); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean eliminarSubtipoAsesoriaDto(SubtipoAsesoriaDao dao, SubtipoAsesoriaDto dto) {
        boolean correcto = false;
        try { correcto = dao.eliminar(dto.getId()).equals(dto); }
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static void main(String[] args) {
        SubtipoAsesoriaDao dao = new SubtipoAsesoriaDaoJdbc();
        TipoAsesoriaDao tDao = new TipoAsesoriaDaoJdbc();
        
        TipoAsesoriaDto tipo = new TipoAsesoriaDto();
        tipo.setNombre("TipoAsesoria");
        tipo.setDescripcion("Descripción del tipo de asesoria 1");
        tipo.setHabilitado(true);
        tDao.guardar(tipo);
        
        SubtipoAsesoriaDto tipoUno = new SubtipoAsesoriaDto();
        tipoUno.setNombre("SubtipoAsesoria 1");
        tipoUno.setTipo(tipo);
        tipoUno.setDescripcion("Descripción del tipo de asesoria 1");
        tipoUno.setHabilitado(true);
        guardarSubtipoAsesoria(dao, tipoUno);
        obtenerSubtipoAsesoria(dao, tipoUno);
        tipoUno.setDescripcion("Nueva descripción");
        guardarSubtipoAsesoria(dao, tipoUno);
        
        SubtipoAsesoriaDto tres = new SubtipoAsesoriaDto();
        tres.setNombre("SubtipoAsesoria 3");
        tres.setDescripcion("Descripción");
        tres.setTipo(tipo);
        tres.setHabilitado(false);
        guardarSubtipoAsesoria(dao, tres);
        
        List<SubtipoAsesoriaDto> habilitados = new ArrayList<SubtipoAsesoriaDto>();
        habilitados.add(tipoUno);
        obtenerHabilitados(dao, habilitados);
        
        List<SubtipoAsesoriaDto> todos = new ArrayList<SubtipoAsesoriaDto>();
        todos.add(tipoUno);
        todos.add(tres);
        obtenerTodos(dao, todos);
        
        eliminarSubtipoAsesoriaDto(dao, tipoUno);
        eliminarSubtipoAsesoriaDto(dao, tres);
        tDao.eliminar(tipo.getId());
    }
}
