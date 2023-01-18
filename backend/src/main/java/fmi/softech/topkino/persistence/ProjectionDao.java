package fmi.softech.topkino.persistence;

import fmi.softech.topkino.exceptions.DaoException;
import fmi.softech.topkino.models.Projection;

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
import java.sql.Timestamp;

import java.util.Calendar;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectionDao {

    public List<Projection> getAll() throws DaoException, ConstraintViolationException {
        Session dbSession = DBDriver.getSessionFactory().openSession();
        try {
            return dbSession.createQuery("from Projection", Projection.class).list();
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            dbSession.close();
        }
    }
    public Projection getOneById(Long id) throws DaoException {
        Session dbSession = DBDriver.getSessionFactory().openSession();
        try {
            // return projection
            return dbSession.get(Projection.class, id);
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            dbSession.close();
        }
    }

    public static Timestamp addDays(Timestamp date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);// w ww.  j ava  2  s  .co m
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return new Timestamp(cal.getTime().getTime());

    }

    public List<Projection> getAllFiltered(Projection projection) throws DaoException, ConstraintViolationException {
        Session dbSession = DBDriver.getSessionFactory().openSession();
        try {
            CriteriaBuilder cb = dbSession.getCriteriaBuilder();
            CriteriaQuery<Projection> cr = cb.createQuery(Projection.class);
            Root<Projection> root = cr.from(Projection.class);

            List<Predicate> predicates = new ArrayList<>();

            if(projection.getMovie() != null ) {
                predicates.add(cb.equal(root.get("movie"), projection.getMovie()));
            }

            if(projection.getProjectionOn() != null) {
                predicates.add(cb.between(root.get("projectionOn"), projection.getProjectionOn(), addDays(projection.getProjectionOn(), 1)));
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
    public Projection addProjection (Projection projection) throws DaoException, PersistenceException {
        Session dbSession = DBDriver.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            // start a transaction
            transaction = dbSession.beginTransaction();

            // save the object
            dbSession.merge(projection);

            // commit transaction
            transaction.commit();

            return projection;
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

    public Projection updateProjection (Projection projection) throws DaoException, PersistenceException {
        Session dbSession = DBDriver.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            // start a transaction
            transaction = dbSession.beginTransaction();

            // save the projection object
            dbSession.merge(projection);

            // commit transaction
            transaction.commit();

            return projection;
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

    public void deleteProjection (Long projectionID) throws DaoException, EntityNotFoundException {
        Session dbSession = DBDriver.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            // start a transaction
            transaction = dbSession.beginTransaction();

            // remove the projection object
            Projection projection = dbSession.get(Projection.class, projectionID);

            if (projection != null) {
                dbSession.remove(projection);
            } else {
                throw new EntityNotFoundException("Projection with id " + projectionID + " not found");
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
}