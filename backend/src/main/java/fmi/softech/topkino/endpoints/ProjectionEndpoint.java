package fmi.softech.topkino.endpoints;

import fmi.softech.topkino.exceptions.NotFoundException;
import fmi.softech.topkino.models.Projection;
import fmi.softech.topkino.services.ProjectionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/projections")
public class ProjectionEndpoint {
    private final ProjectionService projectionService;

    @Autowired
    public ProjectionEndpoint(ProjectionService projectionService) {
        this.projectionService = projectionService;
    }
    @GetMapping
    public List<Projection> getAll() {
        return projectionService.getAll();
    }

    @GetMapping(value = "/{id}")
    public Projection getOneById(@PathVariable("id") Long id) {
        try {
            return projectionService.getOneById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Projection not found");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Projection addProjection(@RequestBody Projection projection) {
        try {
            return projectionService.addProjection(projection);
        } catch (PersistenceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Projection with this name already exists");
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not process entity");
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Projection updateProjection(@PathVariable("id") Long id, @RequestBody Projection projection) {
        try {
            return projectionService.updateProjection(id, projection);
        } catch (PersistenceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INCORRECT INPUT");
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not process entity");
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProjection(@PathVariable("id") Long id) {
        try {
            projectionService.deleteProjection(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Projection not found");
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not process entity");
        }
    }
}
