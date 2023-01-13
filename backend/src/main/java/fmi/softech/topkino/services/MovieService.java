package fmi.softech.topkino.services;

import fmi.softech.topkino.exceptions.DaoException;
import fmi.softech.topkino.exceptions.NotFoundException;
import fmi.softech.topkino.models.Movie;
import fmi.softech.topkino.models.Movie;
import fmi.softech.topkino.models.Movie;
import fmi.softech.topkino.persistence.MovieDao;
import fmi.softech.topkino.persistence.MovieDao;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
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
        try {
            return movieDao.getAll();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Movie> getAllFiltered(Movie movie) {
        try {
            return movieDao.getAllFiltered(movie);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }
    public Movie getOneById(Long id) throws NotFoundException {
        try {
            return movieDao.getOneById(id);
        } catch (DaoException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    public Movie addMovie(Movie movie) throws PersistenceException {
        try {
            return movieDao.addMovie(movie);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public Movie updateMovie(Long movieID, Movie movie) throws PersistenceException {
        try {
            movie.setId(movieID);
            return movieDao.updateMovie(movie);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteMovie(Long movieID) throws EntityNotFoundException {
        try {
            movieDao.deleteMovie(movieID);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }
}
