/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.dao.test;

import java.util.ArrayList;
import java.util.List;
import mx.unam.dgtic.modelo.dao.TipoAsesoriaDao;
import mx.unam.dgtic.modelo.dao.TipoAsesoriaDaoJdbc;
import mx.unam.dgtic.modelo.dto.TipoAsesoriaDto;

/**
 *
 * @author alexis.suarez
 */
public class TipoAsesoriaDaoTest {
    public static boolean guardarTipoAsesoria(TipoAsesoriaDao dao, TipoAsesoriaDto dto) {
        boolean correcto = false;
        
        try { correcto = dao.guardar(dto).equals(dto); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean obtenerTipoAsesoria(TipoAsesoriaDao dao, TipoAsesoriaDto dto) {
        boolean correcto = false;
        
        try {  correcto = dto.equals(dao.obtener(dto.getId())); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean obtenerHabilitados(TipoAsesoriaDao dao, List<TipoAsesoriaDto> lista) {
        boolean correcto = false;
        try { correcto = dao.obtenerHabilitados().equals(lista); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean obtenerTodos(TipoAsesoriaDao dao, List<TipoAsesoriaDto> lista) {
        boolean correcto = false;
        try { correcto = dao.obtenerTodos().equals(lista); } 
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static boolean eliminarTipoAsesoriaDto(TipoAsesoriaDao dao, TipoAsesoriaDto dto) {
        boolean correcto = false;
        try { correcto = dao.eliminar(dto.getId()).equals(dto); }
        catch(Exception e) { e.printStackTrace(); }
        return correcto;
    }
    
    public static void main(String[] args) {
        TipoAsesoriaDao dao = new TipoAsesoriaDaoJdbc();
        
        TipoAsesoriaDto tipoUno = new TipoAsesoriaDto();
        tipoUno.setNombre("TipoAsesoria 1");
        tipoUno.setDescripcion("Descripción del tipo de asesoria 1");
        tipoUno.setHabilitado(true);
        guardarTipoAsesoria(dao, tipoUno);
        obtenerTipoAsesoria(dao, tipoUno);
        tipoUno.setDescripcion("Nueva descripción");
        guardarTipoAsesoria(dao, tipoUno);
        
        TipoAsesoriaDto tres = new TipoAsesoriaDto();
        tres.setNombre("TipoAsesoria 3");
        tres.setHabilitado(false);
        guardarTipoAsesoria(dao, tres);
        
        List<TipoAsesoriaDto> habilitados = new ArrayList<TipoAsesoriaDto>();
        habilitados.add(tipoUno);
        obtenerHabilitados(dao, habilitados);
        
        List<TipoAsesoriaDto> todos = new ArrayList<TipoAsesoriaDto>();
        todos.add(tipoUno);
        todos.add(tres);
        obtenerTodos(dao, todos);
        
        eliminarTipoAsesoriaDto(dao, tipoUno);
        eliminarTipoAsesoriaDto(dao, tres);
        
    }
}
