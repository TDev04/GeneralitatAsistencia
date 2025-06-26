package org.vinyes.asistencia.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://direccion_ip:puerto/nombre_bbdd";
    private static final String USER = "nombre_usuario";
    private static final String PASSWORD = "contraseña";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}