package fmi.softech.topkino.persistence;

import fmi.softech.topkino.exceptions.DaoException;
import fmi.softech.topkino.models.Movie;
import fmi.softech.topkino.models.Movie;
import fmi.softech.topkino.models.Movie;
import fmi.softech.topkino.utils.DBDriver;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
    public List<Movie> getAllFiltered(Movie movie) throws DaoException, ConstraintViolationException {
        try {
            CriteriaBuilder cb = dbSession.getCriteriaBuilder();
            CriteriaQuery<Movie> cr = cb.createQuery(Movie.class);
            Root<Movie> root = cr.from(Movie.class);

            List<Predicate> predicates = new ArrayList<>();

            if(movie.getTitle() != null) {
                predicates.add(cb.like(root.get("title"), movie.getTitle()));
            }

            if(movie.getGenre() != null) {
                predicates.add(cb.equal(root.get("genre"), movie.getGenre()));
            }

            if(movie.getReleaseYear() != null) {
                predicates.add(cb.ge(root.get("releaseYear"), movie.getReleaseYear()));
            }

            Predicate[] predicatesArray = new Predicate[predicates.size()];
            predicates.toArray(predicatesArray);

            cr.select(root).where(predicatesArray);

            return dbSession.createQuery(cr).list();
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