package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConeccionMySQL {

    private String database_nombre = "farmacia_database";
    private String usuario = "root";
    private String contraseña = "admin";
    private String url = "jdbc:mysql://localhost:3306/" + database_nombre;

    Connection conn = null;

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, usuario, contraseña);
        } catch (ClassNotFoundException e) {
            System.err.println("Ha ocurrido un ClassNotFoundException:" + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Ha ocurrido un SQLException:" + e.getMessage());
        }
        return conn;
    }
}
