package fmi.softech.topkino.endpoints;

import fmi.softech.topkino.models.Room;
import fmi.softech.topkino.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomEndpoint {
    private final RoomService roomService;

    @Autowired
    public RoomEndpoint(RoomService roomService) {
        this.roomService = roomService;
    }
    @GetMapping
    public List<Room> getAll() {
        return roomService.getAll();
    }

    @GetMapping(value = "/{id}")
    public Room getOneById(@PathVariable("id") Long id) {
        return roomService.getOneById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addRoom(@RequestBody Room room) {
        roomService.addRoom(room);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateRoom(@PathVariable("id") Long id, @RequestBody Room room) {
        roomService.updateRoom(id, room);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable("id") Long id) {
        roomService.deleteRoom(id);
    }
}
