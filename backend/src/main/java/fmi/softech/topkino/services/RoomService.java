package fmi.softech.topkino.services;

import fmi.softech.topkino.exceptions.DaoException;
import fmi.softech.topkino.exceptions.NotFoundException;
import fmi.softech.topkino.models.Room;
import fmi.softech.topkino.persistence.RoomDao;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomDao roomDao;

    @Autowired
    public RoomService(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public List<Room> getAll() {
        try {
            return roomDao.getAll();
        } catch (DaoException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Room getOneById(Long id) throws NotFoundException {
        try {
            return roomDao.getOneById(id);
        } catch (DaoException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    public Room addRoom(Room room) throws PersistenceException {
        try {
            return roomDao.addRoom(room);
        } catch (DaoException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Room updateRoom(Long roomID, Room room) throws PersistenceException {
        try {
            room.setId(roomID);
            return roomDao.updateRoom(room);
        } catch (DaoException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteRoom(Long roomID) throws EntityNotFoundException {
        try {
            roomDao.deleteRoom(roomID);
        } catch (DaoException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
