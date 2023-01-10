package fmi.softech.topkino.persistence;

import fmi.softech.topkino.models.Movie;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieDao {

    public List<Movie> getAll() {
        return new ArrayList<>();
    }

    public Movie getOneById(Long id) {
        return new Movie();
    }

    public void addMovie(Movie movie) {

    }

    public void updateMovie(Long movieID, Movie movie) {

    }

    public void deleteMovie(Long movieID) {

    }
}