/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreriasqliteejemplo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import static libreriasqliteejemplo.LibreriaSQLiteEjemplo.connect;

/**
 *
 * @author cromerofajar
 */
public class LibreriaSQLiteEjemplo {
     /**
     * Connect to a sample database
     */
    public static void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:test.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
         public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:test.db";
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS casablanca (\n"
                + "	fecha integer PRIMARY KEY,\n"
                + "	presidente text NOT NULL,\n"
                + "	capacidad real\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

/**
 *
 * @author sqlitetutorial.net
 */
public static class InsertApp {
 
    /**
     * Connect to the test.db database
     *
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:test.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
 
    /**
     * Insert a new row into the warehouses table
     *
     * @param name
     * @param capacity
     */
    public void insert(String name, int capacity) {
        String sql = "INSERT INTO casablanca(fecha,presidente) VALUES(?,?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, capacity);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
            System.out.println("Insertado");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
 
    /**
     * @param args the command line arguments
     */
}
public static class SelectApp {
 
    /**
     * Connect to the test.db database
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:test.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
 
    
    /**
     * select all rows in the warehouses table
     */
    public void selectAll(){
        String sql = "SELECT fecha, presidente FROM casablanca";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("fecha") +  "\t" + 
                                   rs.getString("presidente"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void selectFecha(){
        String ano=JOptionPane.showInputDialog(null,"Introduce el año para saber que presidente estaba al poder");
        String sql = "SELECT fecha, presidente FROM casablanca WHERE fecha="+ano;
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("fecha") +  "\t" + 
                                   rs.getString("presidente"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void selectPresi(){
        String presi=JOptionPane.showInputDialog(null,"Introduce el presidente para saber que años estaba al poder");
        String sql = "SELECT fecha FROM casablanca WHERE presidente=\""+presi+"\"";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            System.out.println(presi+" estubo al poder los años:");
            while (rs.next()) {
                
                System.out.println(rs.getInt("fecha"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

    public static void main(String[] args) {
        connect();
        createNewTable();
        
        InsertApp ins=new InsertApp();
        ins.connect();
        String presi=JOptionPane.showInputDialog(null,"Introduzca el nombre del presidente");
        int fecha=Integer.parseInt(JOptionPane.showInputDialog(null,"Introduzca el año en el que presidio"));
        ins.insert(presi, fecha);
        
        SelectApp sel=new SelectApp();
        sel.connect();
        sel.selectAll();
        sel.selectFecha();
        sel.selectPresi();
    }
    
}
