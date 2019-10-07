/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.dao;

import java.util.List;
import mx.unam.dgtic.modelo.dto.AsesoriaDto;

/**
 *
 * @author alexis.suarez
 */
public interface AsesoriaDao {
    /**
     * Almacena los datos de un Asesoria
     * @param tipo
     * @return 
     */
    public abstract AsesoriaDto guardar(AsesoriaDto tipo);
    
    /**
     * Elimina el registro con los datos de una Asesoria
     * En caso de violación de integridad por llave foránea
     * se actualiza el registro marcándolo como no-habilitado.
     * @param id
     * @return 
     */
    public abstract AsesoriaDto eliminar(short id);
    
    /**
     * Obtiene el Asesoria con el id solicitado
     * @param id
     * @return 
     */
    public abstract AsesoriaDto obtener(short id);
    
    /**
     * Obtiene la lista de todos los Asesorias almacenados
     * @return 
     */
    public abstract List<AsesoriaDto> obtenerTodos();
    
    /**
     * Obtiene la lista de todos los Asesorias habilitados
     * @return 
     */
    public abstract List<AsesoriaDto> obtenerHabilitados();
}
