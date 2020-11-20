package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import javax.transaction.Transactional;
import java.util.List;
@Transactional
public interface UserDao {
    @Transactional
    void createUsersTable();
    @Transactional
    void dropUsersTable();
    @Transactional
    void saveUser(String name, String lastName, byte age);
    @Transactional
    void removeUserById(long id);
    @Transactional
    List<User> getAllUsers();
    @Transactional
    void cleanUsersTable();
}
