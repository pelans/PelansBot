package org.pelans.wordlediscordbo.SQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSQL {

    private static Connection connection;

    public static void createConexion() {
        if(connection != null)
            return;

        String url = "jdbc:sqlite:WordleDiscordBot.db";
        connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    public static Connection getConnection() {
        return connection;
    }

}
