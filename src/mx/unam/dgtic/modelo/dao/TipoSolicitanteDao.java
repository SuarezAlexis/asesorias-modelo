/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.dao;

import java.util.List;
import mx.unam.dgtic.modelo.dto.TipoSolicitanteDto;

/**
 *
 * @author alexis.suarez
 */
public interface TipoSolicitanteDao {
    /**
     * Almacena los datos de un TipoSolicitante
     * @param tipo
     * @return 
     */
    public abstract TipoSolicitanteDto guardar(TipoSolicitanteDto tipo);
    
    /**
     * Elimina el registro con los datos de un TipoSolicitante
     * En caso de violación de integridad por llave foránea
     * se actualiza el registro marcándolo como no-habilitado.
     * @param id
     * @return 
     */
    public abstract TipoSolicitanteDto eliminar(String nombre);
    
    /**
     * Obtiene el TipoSolicitante con el id solicitado
     * @param id
     * @return 
     */
    public abstract TipoSolicitanteDto obtener(String nombre);
    
    /**
     * Obtiene la lista de todos los TipoSolicitante almacenados
     * @return 
     */
    public abstract List<TipoSolicitanteDto> obtenerTodos();
    
    /**
     * Obtiene la lista de todos los TipoSolicitante habilitados
     * @return 
     */
    public abstract List<TipoSolicitanteDto> obtenerHabilitados();
}
