package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DB_USER = "Johny";
    private static final String DB_USER_PASSWORD = "p12345678P";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pre_project";

    public static Connection getConnection() {
        Connection connection = null;
        try {
             connection = DriverManager.getConnection(DB_URL, DB_USER, DB_USER_PASSWORD);
            System.out.println("Соединение с БД успешно!");
        } catch (SQLException sqe) {
            System.out.println("Невозможно установить соединение с БД! " + sqe.getMessage());
        }
        return connection;
    }

}
