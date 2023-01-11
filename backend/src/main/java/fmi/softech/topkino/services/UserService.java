package fmi.softech.topkino.services;

import fmi.softech.topkino.exceptions.DaoException;
import fmi.softech.topkino.exceptions.NotFoundException;
import fmi.softech.topkino.models.User;
import fmi.softech.topkino.persistence.UserDao;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAll() {
        try {
            return userDao.getAll();
        } catch (DaoException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public User getOneById(Long id) throws NotFoundException {
        try {
            return userDao.getOneById(id);
        } catch (DaoException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    public User addUser(User user) throws PersistenceException {
        try {
            String hashedPass = DigestUtils.sha256Hex(user.getPassword());
            user.setPassword(hashedPass);
            return userDao.addUser(user);
        } catch (DaoException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public User updateUser(Long userID, User user) throws PersistenceException {
        try {
            user.setId(userID);
            String hashedPass = DigestUtils.sha256Hex(user.getPassword());
            User currentUser = getOneById(userID);

            if(!hashedPass.equals(currentUser.getPassword())) {
                user.setPassword(hashedPass);
            }

            return userDao.updateUser(user);
        } catch (DaoException e) {
            throw new RuntimeException(e.getMessage());
        } catch (NotFoundException e) {
            throw new PersistenceException(e);
        }
    }

    public void deleteUser(Long userID) throws EntityNotFoundException {
        try {
            userDao.deleteUser(userID);
        } catch (DaoException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
