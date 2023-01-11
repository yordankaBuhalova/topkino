package fmi.softech.topkino.persistence;

import fmi.softech.topkino.exceptions.DaoException;
import fmi.softech.topkino.models.Movie;
import fmi.softech.topkino.models.Movie;
import fmi.softech.topkino.utils.DBDriver;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieDao {
    private final Session dbSession;

    @Autowired
    public MovieDao() {
        dbSession = DBDriver.getSessionFactory().openSession();
    }
    public List<Movie> getAll() throws DaoException, ConstraintViolationException {
        try {
            return dbSession.createQuery("from Movie", Movie.class).list();
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    public Movie getOneById(Long id) throws DaoException {
        try {
            // return movie
            return dbSession.get(Movie.class, id);
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    public Movie addMovie(Movie movie) throws DaoException, PersistenceException {
        Transaction transaction = null;
        try {
            // start a transaction
            transaction = dbSession.beginTransaction();

            // save the object
            dbSession.persist(movie);

            // commit transaction
            transaction.commit();

            return movie;
        } catch (PersistenceException e) {
            throw e;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e.getMessage());
        }
    }

    public Movie updateMovie(Movie movie)throws DaoException, PersistenceException  {
        Transaction transaction = null;
        try {
            // start a transaction
            transaction = dbSession.beginTransaction();

            // save the movie object
            dbSession.merge(movie);

            // commit transaction
            transaction.commit();

            return movie;
        } catch (PersistenceException e) {
            throw e;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e.getMessage());
        }
    }

    public void deleteMovie(Long movieID) throws DaoException, EntityNotFoundException {
        Transaction transaction = null;
        try {
            // start a transaction
            transaction = dbSession.beginTransaction();

            // remove the movie object
            Movie movie = dbSession.get(Movie.class, movieID);

            if(movie != null) {
                dbSession.remove(movie);
            } else {
                throw new EntityNotFoundException("Movie with id " + movieID + " not found");
            }
            // commit transaction
            transaction.commit();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e.getMessage());
        }
    }
}