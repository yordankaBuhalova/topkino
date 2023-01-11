package fmi.softech.topkino.persistence;

import fmi.softech.topkino.exceptions.DaoException;
import fmi.softech.topkino.models.Room;
import fmi.softech.topkino.utils.DBDriver;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomDao {
    private Session dbSession;

    @Autowired
    public RoomDao() {
        dbSession = DBDriver.getSessionFactory().openSession();
    }

    public List<Room> getAll() throws DaoException {
        try {
            return dbSession.createQuery("from Room", Room.class).list();
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    public Room getOneById(Long id) throws DaoException {
        try {
            // return room
            return dbSession.get(Room.class, id);
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    public void addRoom(Room room) throws DaoException {
        Transaction transaction = null;
        try {
            // start a transaction
            transaction = dbSession.beginTransaction();

            // save the object
            dbSession.persist(room);

            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e.getMessage());
        }
    }

    public void updateRoom(Room room) throws DaoException {
        Transaction transaction = null;
        try {
            // start a transaction
            transaction = dbSession.beginTransaction();

            // save the room object
            dbSession.merge(room);

            // commit transaction
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e.getMessage());
        }
    }

    public void deleteRoom(Long roomID) throws DaoException {
        Transaction transaction = null;
        try {
            // start a transaction
            transaction = dbSession.beginTransaction();

            // remove the room object
            Room room = dbSession.get(Room.class, roomID);
            dbSession.remove(room);

            // commit transaction
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e.getMessage());
        }
    }
}
