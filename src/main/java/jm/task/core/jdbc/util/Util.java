package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import static java.util.Map.entry;

public class Util {
    private static final String DB_USER = "Johny";
    private static final String DB_USER_PASSWORD = "p12345678P";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pre_project";
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";

    private static SessionFactory sessionFactory;
    //private static StandardServiceRegistry serviceRegistry;

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

    public static SessionFactory getSessionFactory(){
        if (sessionFactory == null) {

            StandardServiceRegistryBuilder rb = new StandardServiceRegistryBuilder();

            /*Map<String, Object> settings = new HashMap<>();
            settings.put("hibernate.connection.driver_class", DB_DRIVER);
            settings.put("hibernate.connection.url", DB_URL);
            settings.put("hibernate.connection.username", DB_USER);
            settings.put("hibernate.connection.password", DB_USER_PASSWORD);
            settings.put("hibernate.connection.show_sql", true);
            settings.put("hibernate.connection.format_sql", true);*/

            //java 9: settings now immutable
            Map<String, Object> settings = Map.ofEntries(
                    //entry("hibernate.connection.driver_class", DB_DRIVER),
                    entry("hibernate.connection.url", DB_URL),
                    entry("hibernate.connection.username", DB_USER),
                    entry("hibernate.connection.password", DB_USER_PASSWORD),
                    entry("hibernate.connection.show_sql", true),
                    entry("hibernate.connection.format_sql", true)
                    //entry("hibernate.hbm2ddl.auto", "create") // update? / create-drop /drop-and-create /
            );

            rb.applySettings(settings);
            StandardServiceRegistry serviceRegistry = rb.build();

            MetadataSources sources = new MetadataSources(serviceRegistry)
                    .addAnnotatedClass(User.class);

            try {

                sessionFactory = sources
                        .buildMetadata()
                        .buildSessionFactory();

            } catch (HibernateException he) {
                System.out.println("Не удалось создать фабрику сессий! " + he.getMessage());
                StandardServiceRegistryBuilder.destroy(serviceRegistry);
            }
        }
        return sessionFactory;
    }

    /*public static void shutdown() {
        if (serviceRegistry != null) {
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
    }*/
}
