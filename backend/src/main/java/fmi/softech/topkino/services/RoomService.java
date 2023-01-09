package fmi.softech.topkino.services;

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
        return roomDao.getAll();
    }

    public Room getOneById(Long id) {
        return roomDao.getOneById(id);
    }

    public void addRoom(Room room) {
        roomDao.addRoom(room);
    }

    public void updateRoom(Long roomID, Room room) {
        roomDao.updateRoom(roomID, room);
    }

    public void deleteRoom(Long roomID) {
        roomDao.deleteRoom(roomID);
    }
}
