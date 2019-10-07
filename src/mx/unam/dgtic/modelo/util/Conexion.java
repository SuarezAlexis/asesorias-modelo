/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author JAVA
 */
public interface Conexion {
    Connection getConnection() throws SQLException;
    void close(Object stream);
}
