package fmi.softech.topkino.persistence;

import fmi.softech.topkino.exceptions.DaoException;
import fmi.softech.topkino.models.Movie;
import fmi.softech.topkino.models.User;
import fmi.softech.topkino.utils.DBDriver;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    private final Session dbSession;

    @Autowired
    public UserDao() {
        dbSession = DBDriver.getSessionFactory().openSession();
    }

    public List<User> getAll() throws DaoException, ConstraintViolationException {
        try {
            return dbSession.createQuery("from User", User.class).list();
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    public User getOneById(Long id) throws DaoException {
        try {
            // return user
            return dbSession.get(User.class, id);
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    public User addUser(User user) throws DaoException, PersistenceException {
        Transaction transaction = null;
        try {
            // start a transaction
            transaction = dbSession.beginTransaction();

            // save the object
            dbSession.persist(user);

            // commit transaction
            transaction.commit();

            return user;
        } catch (PersistenceException e) {
            throw e;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e.getMessage());
        }
    }

    public User updateUser(User user) throws DaoException, PersistenceException  {
        Transaction transaction = null;
        try {
            // start a transaction
            transaction = dbSession.beginTransaction();

            // save the user object
            dbSession.merge(user);

            // commit transaction
            transaction.commit();

            return user;
        } catch (PersistenceException e) {
            throw e;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e.getMessage());
        }
    }

    public void deleteUser(Long userID) throws DaoException, EntityNotFoundException {
        Transaction transaction = null;
        try {
            // start a transaction
            transaction = dbSession.beginTransaction();

            // remove the user object
            User user = dbSession.get(User.class, userID);

            if(user != null) {
                dbSession.remove(user);
            } else {
                throw new EntityNotFoundException("User with id " + userID + " not found");
            }
            // commit transaction
            transaction.commit();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e.getMessage());
        }
    }
}

