/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.dao;

import java.util.List;
import mx.unam.dgtic.modelo.dto.UsuarioDto;

/**
 *
 * 
 */
public interface UsuarioDao {
    /**
     * Almacena los datos de un usuario y devuelve el registro completo.
     * Crea un nuevo registro si el id es menor a cero, en otro caso
     * actualiza el regostro correspondiente.
     * @param u
     * @return Los datos del registro insertado o actualizado.
     */
    public abstract UsuarioDto guardar(UsuarioDto u);
    
    /**
     * Elimina el registro de un usuario o lo deshabilita en caso de violación
     * de llave foránea.
     * @param username
     * @return El registro eliminado o inhabilitado.
     */
    public abstract UsuarioDto eliminar(String username);
    
    /**
     * Verifica si el par de credenciales (usuario,contraseña) proporcionadas
     * coincide con las de algún registro almacenado. Si el usuario es auténtico
     * devuelve sus datos, en otro caso se devuelve null.
     * @param username
     * @param password
     * @return 
     */
    public abstract UsuarioDto autenticar(String username, String password);
    
    /**
     * Obtiene los datos del usuario proporcionado.
     * @param username
     * @return 
     */
    public abstract UsuarioDto obtener(String username);
    
    /**
     * Obtiene la lista de todos los usuarios.
     * @return 
     */
    public abstract List<UsuarioDto> obtenerTodos();
}
