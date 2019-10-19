/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * 
 */
class ConexionMySql implements Conexion {
    private static ConexionMySql instance;
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Asesorias";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "mysql";
    private Driver driver = null;
    
    private ConexionMySql() {
        if(driver == null) {
            try {
                Class jdbcDriverClass = Class.forName(JDBC_DRIVER);
                driver = (Driver) jdbcDriverClass.newInstance();
                DriverManager.registerDriver(driver);
            } catch(Exception e) {
                System.out.println("Fallo al cargar el driver JDBC para mysql.");
                e.printStackTrace();
            }
        }
    }
    
    public static ConexionMySql getInstance() {
        if (instance == null) {
            instance = new ConexionMySql();
        }
        return instance;
    }
    
    public synchronized Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }
    
    public void close(Object stream) {
        if (stream != null) {
            try {
                if (stream instanceof Statement) {
                    ((Statement) stream).close();
                } else if (stream instanceof PreparedStatement) {
                    ((PreparedStatement)stream).close();
                } else if (stream instanceof CallableStatement) {
                    ((CallableStatement)stream).close();
                } else if (stream instanceof ResultSet) {
                    ((ResultSet)stream).close();
                } else if (stream instanceof Connection) {
                    ((Connection)stream).close();
                }
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        }
    }
}
