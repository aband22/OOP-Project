package Servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static Connection connection;
    private static final String URL_ = "jdbc:mysql://localhost:3306/oop";
    private static final String USER_ = "root";
    private static final String PASSWORD_ = "sazulavi10911";

    public static Connection getInstance() throws SQLException {
        ConnectionManager manager = new ConnectionManager();
        return manager.getConnection();
    }

    private ConnectionManager() throws SQLException {
        connection = DriverManager.getConnection(
                URL_,
                USER_,
                PASSWORD_
        );
    }

    private Connection getConnection() {
        return connection;
    }
}
