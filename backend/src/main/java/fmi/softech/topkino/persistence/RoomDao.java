package fmi.softech.topkino.persistence;

import fmi.softech.topkino.exceptions.DaoException;
import fmi.softech.topkino.models.Room;
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
public class RoomDao {

    public List<Room> getAll() throws DaoException, ConstraintViolationException {
        Session dbSession = DBDriver.getSessionFactory().openSession();

        try {
            return dbSession.createQuery("from Room", Room.class).list();
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            dbSession.close();
        }
    }

    public List<Room> getAllFiltered(Room room) throws DaoException, ConstraintViolationException {
        Session dbSession = DBDriver.getSessionFactory().openSession();

        try {
            CriteriaBuilder cb = dbSession.getCriteriaBuilder();
            CriteriaQuery<Room> cr = cb.createQuery(Room.class);
            Root<Room> root = cr.from(Room.class);

            List<Predicate> predicates = new ArrayList<>();

            if(room.getName() != null) {
                predicates.add(cb.like(root.get("name"), room.getName()));
            }

            if(room.getType() != null) {
                predicates.add(cb.equal(root.get("type"), room.getType()));
            }

            if(room.getSeats() != null) {
                predicates.add(cb.ge(root.get("seats"), room.getSeats()));
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

    public Room getOneById(Long id) throws DaoException {
        Session dbSession = DBDriver.getSessionFactory().openSession();
        try {
            // return room
            return dbSession.get(Room.class, id);
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            dbSession.close();
        }
    }

    public Room addRoom(Room room) throws DaoException, PersistenceException {
        Session dbSession = DBDriver.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            // start a transaction
            transaction = dbSession.beginTransaction();

            // save the object
            dbSession.persist(room);

            // commit transaction
            transaction.commit();

            return room;
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

    public Room updateRoom(Room room) throws DaoException, PersistenceException  {
        Session dbSession = DBDriver.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            // start a transaction
            transaction = dbSession.beginTransaction();

            // save the room object
            dbSession.merge(room);

            // commit transaction
            transaction.commit();

            return room;
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

    public void deleteRoom(Long roomID) throws DaoException, EntityNotFoundException {
        Session dbSession = DBDriver.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            // start a transaction
            transaction = dbSession.beginTransaction();

            // remove the room object
            Room room = dbSession.get(Room.class, roomID);

            if(room != null) {
                dbSession.remove(room);
            } else {
                throw new EntityNotFoundException("Room with id " + roomID + " not found");
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
