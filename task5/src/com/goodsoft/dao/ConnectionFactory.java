package com.goodsoft.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public final class ConnectionFactory {

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;
    private static final String SCHEMA;

    static {
        Properties props = new Properties();
        try (InputStream in = ConnectionFactory.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (in == null) {
                throw new ExceptionInInitializerError("Файл db.properties не найден.");
            }

            props.load(in);
            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");
            SCHEMA = props.getProperty("db.schema", "authorization");

            if (URL == null || USER == null || PASSWORD == null) {
                throw new ExceptionInInitializerError(
                        "В db.properties должны быть db.url, db.user и db.password");
            }

            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private ConnectionFactory() {
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        try (Statement statement = connection.createStatement()) {
            statement.execute("SET search_path TO \"" + SCHEMA + "\"");
        }

        return connection;
    }
}
