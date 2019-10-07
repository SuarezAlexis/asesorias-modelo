/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.dao;

import java.util.List;
import mx.unam.dgtic.modelo.dto.TipoAsesoriaDto;

/**
 *
 * @author alexis.suarez
 */
public interface TipoAsesoriaDao {
    /**
     * Almacena los datos de un TipoAsesoria
     * @param tipo
     * @return 
     */
    public abstract TipoAsesoriaDto guardar(TipoAsesoriaDto tipo);
    
    /**
     * Elimina el registro con los datos de un TipoAsesoria
     * En caso de violación de integridad por llave foránea
     * se actualiza el registro marcándolo como no-habilitado.
     * @param id
     * @return 
     */
    public abstract TipoAsesoriaDto eliminar(short id);
    
    /**
     * Obtiene el TipoAsesoria con el id solicitado
     * @param id
     * @return 
     */
    public abstract TipoAsesoriaDto obtener(short id);
    
    /**
     * Obtiene la lista de todos los TipoAsesorias almacenados
     * @return 
     */
    public abstract List<TipoAsesoriaDto> obtenerTodos();
    
    /**
     * Obtiene la lista de todos los TipoAsesorias habilitados
     * @return 
     */
    public abstract List<TipoAsesoriaDto> obtenerHabilitados();
}
