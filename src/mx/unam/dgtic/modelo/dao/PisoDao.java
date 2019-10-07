/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.dao;

import java.util.List;
import mx.unam.dgtic.modelo.dto.PisoDto;

/**
 *
 * @author alexis.suarez
 */
public interface PisoDao {
    /**
     * Almacena los datos de un Piso
     * @param piso
     * @return 
     */
    public abstract PisoDto guardar(PisoDto piso);
    
    /**
     * Elimina el registro con los datos de un Piso
     * En caso de violación de integridad por llave foránea
     * se actualiza el registro marcándolo como no-habilitado.
     * @param id
     * @return 
     */
    public abstract PisoDto eliminar(short id);
    
    /**
     * Obtiene el Piso con el id solicitado
     * @param id
     * @return 
     */
    public abstract PisoDto obtener(short id);
    
    /**
     * Obtiene la lista de todos los Pisos almacenados
     * @return 
     */
    public abstract List<PisoDto> obtenerTodos();
    
    /**
     * Obtiene la lista de todos los Pisos habilitados
     * @return 
     */
    public abstract List<PisoDto> obtenerHabilitados();
}
