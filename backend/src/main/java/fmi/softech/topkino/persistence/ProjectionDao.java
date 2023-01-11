package fmi.softech.topkino.persistence;

import fmi.softech.topkino.exceptions.DaoException;
import fmi.softech.topkino.models.Projection;
import fmi.softech.topkino.models.Projection;
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
public class ProjectionDao {
    private final Session dbSession;

    @Autowired
    public ProjectionDao() {
        dbSession = DBDriver.getSessionFactory().openSession();
    }
    public List<Projection> getAll() throws DaoException, ConstraintViolationException {
        try {
            return dbSession.createQuery("from Projection", Projection.class).list();
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }
    public Projection getOneById(Long id) throws DaoException {
        try {
            // return projection
            return dbSession.get(Projection.class, id);
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    public Projection addProjection (Projection projection) throws DaoException, PersistenceException {
        Transaction transaction = null;
        try {
            // start a transaction
            transaction = dbSession.beginTransaction();

            // save the object
            dbSession.persist(projection);

            // commit transaction
            transaction.commit();

            return projection;
        } catch (PersistenceException e) {
            throw e;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e.getMessage());
        }
    }

    public Projection updateProjection (Projection projection) throws DaoException, PersistenceException {
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
            throw new DaoException(e.getMessage());
        }
    }

    public void deleteProjection (Long projectionID) throws DaoException, EntityNotFoundException {
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
            throw new DaoException(e.getMessage());
        }
    }
}