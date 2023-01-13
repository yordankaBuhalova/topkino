package fmi.softech.topkino.persistence;

import fmi.softech.topkino.exceptions.DaoException;
import fmi.softech.topkino.models.Reservation;
import fmi.softech.topkino.utils.DBDriver;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationDao {

    public List<Reservation> getAll() throws DaoException, ConstraintViolationException {
        Session dbSession = DBDriver.getSessionFactory().openSession();
        try {
            return dbSession.createQuery("from Reservation", Reservation.class).list();
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            dbSession.close();
        }
    }

    public Reservation getOneById(Long id) throws DaoException {
        Session dbSession = DBDriver.getSessionFactory().openSession();
        try {
            // return reservation
            return dbSession.get(Reservation.class, id);
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            dbSession.close();
        }
    }

    public Reservation addReservation(Reservation reservation) throws DaoException, PersistenceException {
        Session dbSession = DBDriver.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            // start a transaction
            transaction = dbSession.beginTransaction();

            // save the object
            dbSession.persist(reservation);

            // commit transaction
            transaction.commit();

            return reservation;
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

    public Reservation updateReservation(Reservation reservation) throws DaoException, PersistenceException  {
        Session dbSession = DBDriver.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            // start a transaction
            transaction = dbSession.beginTransaction();

            // save the reservation object
            dbSession.merge(reservation);

            // commit transaction
            transaction.commit();

            return reservation;
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

    public void deleteReservation(Long reservationID) throws DaoException, EntityNotFoundException {
        Session dbSession = DBDriver.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            // start a transaction
            transaction = dbSession.beginTransaction();

            // remove the reservation object
            Reservation reservation = dbSession.get(Reservation.class, reservationID);

            if(reservation != null) {
                dbSession.remove(reservation);
            } else {
                throw new EntityNotFoundException("Reservation with id " + reservationID + " not found");
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

