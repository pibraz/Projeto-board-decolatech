package br.com.bio.persistence.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionConfig {

    private ConnectionConfig() {}

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/dio-projeto";
        String user = "root";
        String password = "87434584";
        var connection = DriverManager.getConnection(url, user, password);

        connection.setAutoCommit(false);
        return connection;
    }

}