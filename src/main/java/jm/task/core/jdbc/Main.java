package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


/**
 * А нужно ли использовать
 * @param: комментарии
 * @return: такого типа
 *   с анотациями
 */
public class Main {
    public static void main(String[] args) {
        UserService us = new UserServiceImpl();

        us.createUsersTable();
        us.saveUser("Юрий", "Сухоруков", (byte)15);
        us.saveUser("Артёмка", "Петров", (byte)23);
        us.saveUser("Марина", "Седых", (byte)18);
        us.saveUser("Александра", "Захаренко", (byte)38);

        us.getAllUsers().forEach(System.out::println);

        us.cleanUsersTable();
        us.dropUsersTable();
    }
}
