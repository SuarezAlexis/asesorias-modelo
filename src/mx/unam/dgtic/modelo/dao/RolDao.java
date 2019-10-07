/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.dao;

import mx.unam.dgtic.modelo.dto.RolDto;
import java.util.List;

/**
 *
 * 
 */
public interface RolDao {
    /** 
     * Almacena los datos de un Rol
     * Crea un nuevo registro si el id es -1, 
     * en otro caso actualiza el registro correspondiente. 
     * @param rol
     * @return 
     */
    public abstract RolDto guardar(RolDto rol);
    
    /**
     * Elimina el registro con los datos de un rol.
     * En caso de violación de integridad por llave foránea
     * se actualiza el registro marcándolo como no-habilitado.
     * @param id
     * @return Los datos del Rol eliminado o inhabilitado.
     */
    public abstract RolDto eliminar(short id);
    
    /**
     * Obtiene la lista de Roles de un determinado usuario.
     * @param username
     * @return 
     */
    public abstract List<RolDto> obtener(String username);
    
    /**
     * Obtiene el Rol con el id solicitado.
     * @param id
     * @return 
     */
    public abstract RolDto obtener(short id);
    
    /**
     * Obtiene la lista de todos los Roles almacenados.
     * @return 
     */
    public abstract List<RolDto> obtenerTodos();
    
    /**
     * Obtiene la lista de los Roles habilitados.
     * @return 
     */
    public abstract List<RolDto> obtenerHabilitados();    
}
