package fmi.softech.topkino.persistence;

import fmi.softech.topkino.models.Room;
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

    }

    public void updateRoom(Long roomID, Room room) {

    }

    public void deleteRoom(Long roomID) {

    }
}
