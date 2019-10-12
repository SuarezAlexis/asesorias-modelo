/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.dao;

import java.util.List;
import mx.unam.dgtic.modelo.dto.UrlItem;

/**
 *
 * @author JAVA
 */
public interface UrlItemDao {
    
    List<UrlItem> obtenerTodos();
    
    List<UrlItem> obtenerHabilitados();
}
