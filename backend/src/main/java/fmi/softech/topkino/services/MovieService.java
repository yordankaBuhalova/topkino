package fmi.softech.topkino.services;

import fmi.softech.topkino.models.Movie;
import fmi.softech.topkino.persistence.MovieDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieDao movieDao;

    @Autowired
    public MovieService(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public List<Movie> getAll() {
        return movieDao.getAll();
    }

    public Movie getOneById(Long id) {
        return movieDao.getOneById(id);
    }

    public void addMovie(Movie movie) {
        movieDao.addMovie(movie);
    }

    public void updateMovie(Long movieID, Movie movie) {
        movieDao.updateMovie(movieID, movie);
    }

    public void deleteMovie(Long movieID) {
        movieDao.deleteMovie(movieID);
    }
}
