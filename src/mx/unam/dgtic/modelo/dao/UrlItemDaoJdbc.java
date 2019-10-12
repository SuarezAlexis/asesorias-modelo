/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.unam.dgtic.modelo.dto.UrlItem;
import mx.unam.dgtic.modelo.util.Database;

/**
 *
 * @author JAVA
 */
public class UrlItemDaoJdbc implements UrlItemDao {

    private static final String SELECT_SQL = "SELECT * FROM UrlItem";
    
    private static UrlItem resultSetMapper(ResultSet rs) throws SQLException {
        UrlItem i = new UrlItem();
        i.setId(rs.getInt("id"));
        i.setValor(rs.getString("valor"));
        i.setEtiqueta(rs.getString("etiqueta"));
        i.setHabilitado(rs.getBoolean("habilitado"));
        return i;
    }
    
    @Override
    public List<UrlItem> obtenerTodos() {
        List<UrlItem> items = new ArrayList<UrlItem>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL);
            rs = ps.executeQuery();
            while(rs.next()){
                items.add(resultSetMapper(rs));
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            Database.getInstance().close(conn);
        }
        return items;
    }

    @Override
    public List<UrlItem> obtenerHabilitados() {
        List<UrlItem> items = new ArrayList<UrlItem>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = Database.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SQL + " WHERE habilitado = 1");
            rs = ps.executeQuery();
            while(rs.next()){
                items.add(resultSetMapper(rs));
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            Database.getInstance().close(rs);
            Database.getInstance().close(ps);
            Database.getInstance().close(conn);
        }
        return items;
    }
    
}
