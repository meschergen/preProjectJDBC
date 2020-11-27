package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;
    private PreparedStatement statement; // TODO: закрывать его каждый раз
    private String sql; // TODO: убрать


    public UserDaoJDBCImpl() {
        connection = Util.getConnection(); // надо ли его где-то закрывать? где лучше?)
    }

    public void createUsersTable() {
        //нужна ли проверка по метаданным через .getTables(), существует ли таблица? неоязат
        try {
            statement = connection.prepareStatement("CREATE TABLE `pre_project`.`users`"
                    + "(`id` BIGINT NOT NULL AUTO_INCREMENT,"
                    + "`name` VARCHAR(45) NOT NULL,"
                    + "`last_name` VARCHAR(45) NOT NULL,"
                    + "`age` TINYINT NOT NULL, PRIMARY KEY (`id`));");
            statement.execute();
            System.out.println("\nТаблица создана успешно!"); // сообщения в этом классе, или в UserServiceImpl?

        } catch (SQLException sqe) {
            System.out.println("\nНе удалось создать таблицу! " + sqe.getMessage());
        }                                                       // TODO: логировать
    }

    public void dropUsersTable() {
        sql = "DROP TABLE `pre_project`.`users`;";
        try {
            statement = connection.prepareStatement(sql);
            statement.execute();
            System.out.println("\nТаблица удалена успешно!");
        } catch (SQLException sqe) {
            System.out.println("\nНе удалось удалить таблицу!");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        sql = "INSERT INTO `pre_project`.`users` (name, last_name, age) VALUES (?, ?, ?);";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
            System.out.println("\nПользователь " + name + " успешно добавлен!");
        } catch (SQLException sqe) {
            System.out.println("\nНе удалось добавить пользователя " + name + " " + sqe.getMessage());
        }
    }

    public void removeUserById(long id) {
        sql = "DELETE FROM `pre_project`.`users` WHERE `users`.`id`= ?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.execute();

            System.out.println("\nПользователь с id" + id + " успешно удалён!");

        } catch (SQLException sqe) {
            System.out.println("\nНе удалось удалить пользователя с id" + id + " " + sqe.getMessage());
        }
    }

    public List<User> getAllUsers() {
        ResultSet rs;
        User user;
        List<User> allUsers = new LinkedList<>();
        sql = "SELECT * FROM `pre_project`.`users`;";
        try {
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();
            while (rs.next()) {
                user = new User(rs.getLong(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getByte(4));
                allUsers.add(user);
            }

        } catch (SQLException sqe) {
            System.out.println("\nНе удалось получить список пользователей! " + sqe.getMessage());
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        sql = "DELETE FROM `pre_project`.`users`"; // или TRUNCATE?
        try {
            statement = connection.prepareStatement(sql);
            statement.execute();

            System.out.println("\nТаблица очищена успешно!");

        } catch (SQLException sqe) {
            System.out.println("\nНе удалось очистить таблицу! " + sqe.getMessage());
        }
    }
}
