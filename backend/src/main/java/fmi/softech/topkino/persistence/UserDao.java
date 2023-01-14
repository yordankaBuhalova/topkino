package fmi.softech.topkino.persistence;

import fmi.softech.topkino.exceptions.DaoException;
import fmi.softech.topkino.models.User;
import fmi.softech.topkino.utils.DBDriver;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    public List<User> getAll() throws DaoException, ConstraintViolationException {
        Session dbSession = DBDriver.getSessionFactory().openSession();
        try {
            return dbSession.createQuery("from User", User.class).list();
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            dbSession.close();
        }
    }

    public List<User> getAllFiltered(User user) throws DaoException, ConstraintViolationException {
        Session dbSession = DBDriver.getSessionFactory().openSession();
        try {
            CriteriaBuilder cb = dbSession.getCriteriaBuilder();
            CriteriaQuery<User> cr = cb.createQuery(User.class);
            Root<User> root = cr.from(User.class);

            List<Predicate> predicates = new ArrayList<>();

            if(user.getUsername() != null) {
                predicates.add(cb.like(root.get("username"), user.getUsername()));
            }

            if(user.getAdmin() != null) {
                predicates.add(cb.equal(root.get("isAdmin"), user.getAdmin()));
            }

            Predicate[] predicatesArray = new Predicate[predicates.size()];
            predicates.toArray(predicatesArray);

            cr.select(root).where(predicatesArray);

            return dbSession.createQuery(cr).list();
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            dbSession.close();
        }
    }
    
    public User getOneById(Long id) throws DaoException {
        Session dbSession = DBDriver.getSessionFactory().openSession();
        try {
            // return user
            return dbSession.get(User.class, id);
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            dbSession.close();
        }
    }

    public User addUser(User user) throws DaoException, PersistenceException {
        Session dbSession = DBDriver.getSessionFactory().openSession();
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
            throw new DaoException(e);
        } finally {
            dbSession.close();
        }
    }

    public User updateUser(User user) throws DaoException, PersistenceException  {
        Session dbSession = DBDriver.getSessionFactory().openSession();
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
            throw new DaoException(e);
        } finally {
            dbSession.close();
        }
    }

    public void deleteUser(Long userID) throws DaoException, EntityNotFoundException {
        Session dbSession = DBDriver.getSessionFactory().openSession();
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
            throw new DaoException(e);
        } finally {
            dbSession.close();
        }
    }

    public User findUser(User user) throws DaoException {

        Session dbSession = DBDriver.getSessionFactory().openSession();
        try {
            CriteriaBuilder cb = dbSession.getCriteriaBuilder();
            CriteriaQuery<User> cr = cb.createQuery(User.class);
            Root<User> root = cr.from(User.class);

            List<Predicate> predicates = new ArrayList<>();

            if(user.getUsername() != null) {
                predicates.add(cb.equal(root.get("username"), user.getUsername()));
            }

            if(user.getPassword() != null) {
                predicates.add(cb.equal(root.get("password"), user.getPassword()));
            }


            Predicate[] predicatesArray = new Predicate[predicates.size()];
            predicates.toArray(predicatesArray);

            cr.select(root).where(predicatesArray);

            return dbSession.createQuery(cr).uniqueResult();
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            dbSession.close();
        }
    }
}

