/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.dao;

import java.util.List;
import mx.unam.dgtic.modelo.dto.SubtipoAsesoriaDto;

/**
 *
 * @author alexis.suarez
 */
public interface SubtipoAsesoriaDao {
    /**
     * Almacena los datos de un SubtipoAsesoria
     * @param subtipo
     * @return 
     */
    public abstract SubtipoAsesoriaDto guardar(SubtipoAsesoriaDto subtipo);
    
    /**
     * Elimina el registro con los datos de un SubtipoAsesoria
     * En caso de violación de integridad por llave foránea
     * se actualiza el registro marcándolo como no-habilitado.
     * @param id
     * @return 
     */
    public abstract SubtipoAsesoriaDto eliminar(short id);
    
    /**
     * Obtiene el SubtipoAsesoria con el id solicitado
     * @param id
     * @return 
     */
    public abstract SubtipoAsesoriaDto obtener(short id);
    
    /**
     * Obtiene la lista de todos los SubtipoAsesorias almacenados
     * @return 
     */
    public abstract List<SubtipoAsesoriaDto> obtenerTodos();
    
    /**
     * Obtiene la lista de todos los SubtipoAsesorias habilitados
     * @return 
     */
    public abstract List<SubtipoAsesoriaDto> obtenerHabilitados();
}
