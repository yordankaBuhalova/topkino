package fmi.softech.topkino.endpoints;

import fmi.softech.topkino.exceptions.NotFoundException;
import fmi.softech.topkino.models.Room;
import fmi.softech.topkino.models.RoomType;
import fmi.softech.topkino.services.RoomService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping(value="/filter")
    public List<Room> getAllFiltered(@RequestParam(required = false) String name, @RequestParam(required = false) Integer seats, @RequestParam(required = false) RoomType type) {
        Room room = new Room();
        room.setName(name);
        room.setSeats(seats);
        room.setType(type);
        return roomService.getAllFiltered(room);
    }

    @GetMapping(value = "/{id}")
    public Room getOneById(@PathVariable("id") Long id) {
        try {
            return roomService.getOneById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Room addRoom(@RequestBody Room room) {
        try {
            return roomService.addRoom(room);
        } catch (PersistenceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room with this name already exists");
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not process entity");
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Room updateRoom(@PathVariable("id") Long id, @RequestBody Room room) {
        try {
            return roomService.updateRoom(id, room);
        } catch (PersistenceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INCORRECT INPUT");
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not process entity");
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable("id") Long id) {
        try {
            roomService.deleteRoom(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found");
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not process entity");
        }
    }
}
