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

import java.util.ArrayList;
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
            List<User> users = new ArrayList<>();
            for (User user: userDao.getAll()) {
                user.setPassword("");
                users.add(user);
            }
            return users;
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllFiltered(User user) {
        try {
            return userDao.getAllFiltered(user);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public User getExactUser(User user) {
        try {
            String hashedPass = DigestUtils.sha256Hex(user.getPassword());
            user.setPassword(hashedPass);
            return userDao.getExactUser(user);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public User getOneById(Long id) throws NotFoundException {
        try {
            User user = userDao.getOneById(id);
            user.setPassword("");
            return user;
        } catch (DaoException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    public User addUser(User user) throws PersistenceException {
        try {
            String hashedPass = DigestUtils.sha256Hex(user.getPassword());
            user.setPassword(hashedPass);
            User newUser = userDao.addUser(user);
            newUser.setPassword("");
            return newUser;
        } catch (DaoException e) {
            throw new RuntimeException(e);
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
            User newUser = userDao.updateUser(user);
            newUser.setPassword("");
            return newUser;
        } catch (DaoException e) {
            throw new RuntimeException(e);
        } catch (NotFoundException e) {
            throw new PersistenceException(e);
        }
    }

    public void deleteUser(Long userID) throws EntityNotFoundException {
        try {
            userDao.deleteUser(userID);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }
}
