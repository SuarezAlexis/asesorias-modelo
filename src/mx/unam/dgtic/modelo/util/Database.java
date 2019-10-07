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
 * @author alexis.suarez
 */
public class Database implements Conexion {
    private static Conexion conexion;
    
    private Database() { }
    
    public static Conexion getInstance() {
        if(conexion == null) 
        { conexion = ConexionMySql.getInstance(); }
        return conexion;
    }
    
    @Override
    public Connection getConnection() throws SQLException {
        return conexion.getConnection();
    }

    @Override
    public void close(Object stream) {
        conexion.close(stream);
    }
}
