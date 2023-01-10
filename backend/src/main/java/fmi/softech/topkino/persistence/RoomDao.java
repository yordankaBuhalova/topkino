package fmi.softech.topkino.persistence;

import fmi.softech.topkino.models.Room;
import fmi.softech.topkino.utils.DBDriver;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomDao {

    public List<Room> getAll() {
        return new ArrayList<>();
    }

    public Room getOneById(Long id) {
        return new Room();
    }

    public void addRoom(Room room) {
        Transaction transaction = null;
        try (Session session = DBDriver.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the object
            session.persist(room);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateRoom(Long roomID, Room room) {

    }

    public void deleteRoom(Long roomID) {

    }
}
