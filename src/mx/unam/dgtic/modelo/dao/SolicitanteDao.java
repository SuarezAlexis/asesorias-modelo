/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.dao;

import java.util.List;
import mx.unam.dgtic.modelo.dto.SolicitanteDto;

/**
 *
 * @author alexis.suarez
 */
public interface SolicitanteDao {
    /**
     * Almacena los datos de un Solicitante
     * @param tipo
     * @return 
     */
    public abstract SolicitanteDto guardar(SolicitanteDto tipo);
    
    /**
     * Elimina el registro con los datos de un Solicitante
     * En caso de violación de integridad por llave foránea
     * se actualiza el registro marcándolo como no-habilitado.
     * @param id
     * @return 
     */
    public abstract SolicitanteDto eliminar(int id);
    
    /**
     * Obtiene el Solicitante con el id solicitado
     * @param id
     * @return 
     */
    public abstract SolicitanteDto obtener(int id);
    
    /**
     * Obtiene la lista de todos los Solicitantes almacenados
     * @return 
     */
    public abstract List<SolicitanteDto> obtenerTodos();
    
    /**
     * Obtiene la lista de todos los Solicitantes habilitados
     * @return 
     */
    public abstract List<SolicitanteDto> obtenerHabilitados();
}
