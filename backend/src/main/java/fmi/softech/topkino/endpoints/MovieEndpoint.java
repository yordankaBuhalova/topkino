package fmi.softech.topkino.endpoints;

import fmi.softech.topkino.models.Movie;
import fmi.softech.topkino.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/{id}")
    public Movie getOneById(@PathVariable("id") Long id) {
        return movieService.getOneById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addMovie(@RequestBody Movie movie) {
        movieService.addMovie(movie);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateMovie(@PathVariable("id") Long id, @RequestBody Movie movie) {
        movieService.updateMovie(id, movie);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable("id") Long id) {
        movieService.deleteMovie(id);
    }
}
