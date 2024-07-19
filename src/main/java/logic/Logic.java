package logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Logic {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/?user=root";
    private static final String DB_User = "root";
    private static final String DB_PASSWORD = "123456";

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_User, DB_PASSWORD);
            System.out.println("Connection");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return connection;
    }

}


