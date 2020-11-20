package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    @Transactional
    public void createUsersTable() {
        String sql = "CREATE TABLE `pre_project`.`users`"
                + "(`id` BIGINT NOT NULL AUTO_INCREMENT,"
                + "`name` VARCHAR(45) NOT NULL,"
                + "`last_name` VARCHAR(45) NOT NULL,"
                + "`age` TINYINT NOT NULL, PRIMARY KEY (`id`));";

        Transaction t;
        Session session = Util.getSessionFactory().openSession();
        //try-with-resource possible, since hibernate 5.4.*
        // java 9 style
        try(session) {
            t = session.getTransaction();
            t.begin();
            session.createSQLQuery(sql).executeUpdate();
            t.commit();

            System.out.println("\nТаблица создана успешно!");

        } catch(PersistenceException pe) {
            System.out.println("\nНе удалось создать таблицу! " + pe.getMessage());
            //if (t != null) { t.rollback(); }
        }
    }

    @Override
    @Transactional
    public void dropUsersTable() {
        String sql = "DROP TABLE `pre_project`.`users`;";

        Transaction t;
        Session session = Util.getSessionFactory().openSession();
        try(session) {
            t = session.getTransaction();
            t.begin();
            session.createSQLQuery(sql).executeUpdate();
            t.commit();

            System.out.println("\nТаблица удалена успешно!");

        } catch(PersistenceException pe) {
            System.out.println("\nНе удалось удалить таблицу! " + pe.getMessage());
            //if (t != null) { t.rollback(); }
        }
    }

    @Override
    @Transactional
    public void saveUser(String name, String lastName, byte age) {
        Transaction t;
        Session session = Util.getSessionFactory().openSession();
        try(session) {

            t = session.getTransaction();
            t.begin();
            session.save(new User(name, lastName, age));
            t.commit();

            System.out.println("\nПользователь " + name + " успешно добавлен!");

        } catch(PersistenceException pe) {
            System.out.println("\nНе добавить пользователя " + name + "! " + pe.getMessage());
            //if (t != null) { t.rollback(); }
        }
    }

    @Override
    @Transactional
    public void removeUserById(long id) {
        //нужно ли обрабатывать persistence-случаи с ConstraintViolationException?
        Transaction t;
        User user;
        Session session = Util.getSessionFactory().openSession();
        try(session) {
            user = session.get(User.class, id);
            //user = new User();
            //user.setId(id);
            t = session.getTransaction();
            t.begin();
            session.delete(user);
            t.commit();

            System.out.println("\nПользователь с id" + id + " успешно удалён!");

        } catch(PersistenceException pe) {
            System.out.println("\nНе удалось удалить пользователя, с id " + id + "! " + pe.getMessage());
            //if (t != null) { t.rollback(); }
        }
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        List<User> result = null;
        Transaction t;
        Session session = Util.getSessionFactory().openSession();
        try(session) {

            t = session.getTransaction();
            t.begin();
            result = session.createQuery("FROM User", User.class).list();
            t.commit();

        } catch(PersistenceException pe) {
            System.out.println("\nНе удалось получить список пользователей! " + pe.getMessage());
            //if (t != null) { t.rollback(); }
        }
        return result;
    }

    @Override
    @Transactional
    public void cleanUsersTable() {
        Transaction t;
        Session session = Util.getSessionFactory().openSession();
        try(session) {

            t = session.getTransaction();
            t.begin();
            session.createQuery("DELETE FROM User").executeUpdate();
            t.commit();

            System.out.println("\nТаблица очищена успешно!");

        } catch(PersistenceException pe) {
            System.out.println("\nНе удалось очистить таблицу! " + pe.getMessage());
            //if (t != null) { t.rollback(); }
        }

    }
}
