/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author JAVA
 */
class ConexionDataSource implements Conexion {
    
    private static ConexionDataSource instance;
    private DataSource ds;

    /**
     * Creates a new instance of Conexion
     */
    private ConexionDataSource() {
        try {
            InitialContext initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:comp/env");
            ds = (DataSource) envContext.lookup("jdbc/asesorias");
        } catch (NamingException nex) {
            System.out.println("No se pudo abrir la base de datos: " + nex.getMessage());
        }
    }

    public static ConexionDataSource getInstance() {
        if (instance == null) {
            instance = new ConexionDataSource();
        }
        return instance;
    }

    public synchronized Connection getConnection() throws SQLException {
        return ds.getConnection();
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
