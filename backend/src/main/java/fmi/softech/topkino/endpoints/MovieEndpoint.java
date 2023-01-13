package fmi.softech.topkino.endpoints;

import fmi.softech.topkino.exceptions.NotFoundException;
import fmi.softech.topkino.models.Movie;
import fmi.softech.topkino.services.MovieService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieEndpoint {
    private final MovieService movieService;

    @Autowired
    public MovieEndpoint(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getAll() {
        return movieService.getAll();
    }

    @GetMapping(value="/filter")
    public List<Movie> getAllFiltered(@RequestParam(required = false) String title, @RequestParam(required = false) String genre, @RequestParam(required = false) Integer releaseYear) {
        try {
            Movie movie = new Movie();
            movie.setTitle(title);
            movie.setGenre(genre);
            movie.setReleaseYear(releaseYear);
            return movieService.getAllFiltered(movie);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not process entity");
        }
    }

    @GetMapping(value = "/{id}")
    public Movie getOneById(@PathVariable("id") Long id) {
        try {
            return movieService.getOneById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movie addMovie(@RequestBody Movie movie) {
        try {
            return movieService.addMovie(movie);
        } catch (PersistenceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Movie with this name already exists");
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not process entity");
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Movie updateMovie(@PathVariable("id") Long id, @RequestBody Movie movie) {
        try {
            return movieService.updateMovie(id, movie);
        } catch (PersistenceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INCORRECT INPUT");
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not process entity");
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable("id") Long id) {
        try {
            movieService.deleteMovie(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not process entity");
        }
    }
}
