package fmi.softech.topkino.endpoints;

import fmi.softech.topkino.exceptions.NotFoundException;
import fmi.softech.topkino.dtos.ProjectionDto;
import fmi.softech.topkino.mappers.ProjectionMapper;
import fmi.softech.topkino.models.Projection;
import fmi.softech.topkino.services.MovieService;
import fmi.softech.topkino.services.ProjectionService;
import fmi.softech.topkino.services.RoomService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/projections")
public class ProjectionEndpoint {
    private final ProjectionService projectionService;
    private final ProjectionMapper projectionMapper;

    @Autowired
    public ProjectionEndpoint(ProjectionService projectionService, RoomService roomService, MovieService movieService) {
        this.projectionService = projectionService;
        this.projectionMapper = new ProjectionMapper(roomService, movieService);
    }

    @GetMapping
    public List<ProjectionDto> getAll() {
        return projectionMapper.entityListToDtoList(projectionService.getAll());
    }

    @GetMapping(value = "/{id}")
    public ProjectionDto getOneById(@PathVariable("id") Long id) {
        try {
            return projectionMapper.entityToDto(projectionService.getOneById(id));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Projection not found");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectionDto addProjection(@RequestBody ProjectionDto projection) {
        try {
            Projection newProjection = projectionMapper.dtoToEntity(projection);
            return projectionMapper.entityToDto(projectionService.addProjection(newProjection));
        } catch (PersistenceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Projection already exists");
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not process entity");
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectionDto updateProjection(@PathVariable("id") Long id, @RequestBody ProjectionDto projection) {
        try {
            Projection update = projectionMapper.dtoToEntity(projection);
            return projectionMapper.entityToDto(projectionService.updateProjection(id, update));
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
