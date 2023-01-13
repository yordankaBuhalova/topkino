package fmi.softech.topkino.services;

import fmi.softech.topkino.exceptions.DaoException;
import fmi.softech.topkino.exceptions.NotFoundException;
import fmi.softech.topkino.models.Projection;
import fmi.softech.topkino.persistence.ProjectionDao;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectionService {
    private final ProjectionDao projectionDao;

    @Autowired
    public ProjectionService(ProjectionDao projectionDao) {
        this.projectionDao = projectionDao;
    }

    public List<Projection> getAll() {
        try {
            return projectionDao.getAll();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public Projection getOneById(Long id) throws NotFoundException {
        try {
            return projectionDao.getOneById(id);
        } catch (DaoException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    public Projection addProjection(Projection projection) throws PersistenceException {
        try {
            return projectionDao.addProjection(projection);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public Projection updateProjection(Long projectionID, Projection projection) throws PersistenceException {
        try {
            projection.setId(projectionID);
            return projectionDao.updateProjection(projection);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteProjection(Long projectionID) throws EntityNotFoundException {
        try {
            projectionDao.deleteProjection(projectionID);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }
}
