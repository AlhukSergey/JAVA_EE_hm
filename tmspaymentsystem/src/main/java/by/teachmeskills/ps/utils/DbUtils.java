package by.teachmeskills.ps.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/payment_system";
    private static final String DB_USER_NAME = "root";
    private static final String DB_PASSWORD = "1002";

    private DbUtils() {
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
