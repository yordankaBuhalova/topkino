package fmi.softech.topkino.services;

import fmi.softech.topkino.exceptions.DaoException;
import fmi.softech.topkino.exceptions.NotFoundException;
import fmi.softech.topkino.models.Room;
import fmi.softech.topkino.persistence.RoomDao;
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

    public void addRoom(Room room) {
        try {
            roomDao.addRoom(room);
        } catch (DaoException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void updateRoom(Long roomID, Room room) {
        try {
            room.setId(roomID);
            roomDao.updateRoom(room);
        } catch (DaoException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteRoom(Long roomID) {
        try {
            roomDao.deleteRoom(roomID);
        } catch (DaoException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
