package fmi.softech.topkino.endpoints;

import fmi.softech.topkino.exceptions.NotFoundException;
import fmi.softech.topkino.models.User;
import fmi.softech.topkino.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserEndpoint {
    private final UserService userService;

    @Autowired
    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping(value="/filter")
    public List<User> getAllFiltered(@RequestParam(required = false) String name, @RequestParam(required = false) boolean admin) {
        User user = new User();
        user.setUsername(name);
        user.setAdmin(admin);
        return userService.getAllFiltered(user);
    }

    @PostMapping(value="/authorized")
    public Map<String, Boolean> isAuthorized(@RequestBody User user) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("authorized", userService.getExactUser(user) != null);
        return response;
    }

    @GetMapping(value = "/{id}")
    public User getOneById(@PathVariable("id") Long id) {
        try {
            return userService.getOneById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user) {
        try {
            return userService.addUser(user);
        } catch (PersistenceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this name already exists");
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not process entity");
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        try {
            return userService.updateUser(id, user);
        } catch (PersistenceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INCORRECT INPUT");
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not process entity");
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") Long id) {
        try {
            userService.deleteUser(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not process entity");
        }
    }
}
