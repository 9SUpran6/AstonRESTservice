package logic;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Logic {
    public Connection getConnection() {
        ClassLoader classLoader = Logic.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("db.properties")) {
            Properties dataproperties = new Properties();
            dataproperties.load(inputStream);
        String jdbcUrl = dataproperties.getProperty("jdbc.url");
        String username = dataproperties.getProperty("jdbc.username");
        String password = dataproperties.getProperty("jdbc.password");

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connection");
            } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
            }
            return connection;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

