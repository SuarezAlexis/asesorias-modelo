/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.dao;

import java.util.List;
import mx.unam.dgtic.modelo.dto.ActividadDto;

/**
 *
 * @author alexis.suarez
 */
public interface ActividadDao {
    /**
     * Almacena los datos de un Actividad
     * @param tipo
     * @return 
     */
    public abstract ActividadDto guardar(ActividadDto tipo);
    
    /**
     * Elimina el registro con los datos de una Actividad
     * En caso de violación de integridad por llave foránea
     * se actualiza el registro marcándolo como no-habilitado.
     * @param id
     * @return 
     */
    public abstract ActividadDto eliminar(long id);
    
    /**
     * Obtiene el Actividad con el id solicitado
     * @param id
     * @return 
     */
    public abstract ActividadDto obtener(long id);
    
    /**
     * Obtiene las Actividades con de la Asesoria solicitada
     * @param id
     * @return 
     */
    public abstract List<ActividadDto> obtenerAsesoria(long id);
    
    /**
     * Obtiene la lista de todos los Actividads almacenados
     * @return 
     */
    public abstract List<ActividadDto> obtenerTodos();
    
}
