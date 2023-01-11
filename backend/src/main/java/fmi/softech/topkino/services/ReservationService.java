package fmi.softech.topkino.services;

import fmi.softech.topkino.exceptions.DaoException;
import fmi.softech.topkino.exceptions.NotFoundException;
import fmi.softech.topkino.models.Reservation;
import fmi.softech.topkino.persistence.ReservationDao;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;

    @Autowired
    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> getAll() {
        try {
            return reservationDao.getAll();
        } catch (DaoException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Reservation getOneById(Long id) throws NotFoundException {
        try {
            return reservationDao.getOneById(id);
        } catch (DaoException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    public Reservation addReservation(Reservation reservation) throws PersistenceException {
        try {
            return reservationDao.addReservation(reservation);
        } catch (DaoException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Reservation updateReservation(Long reservationID, Reservation reservation) throws PersistenceException {
        try {
            reservation.setId(reservationID);
            return reservationDao.updateReservation(reservation);
        } catch (DaoException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteReservation(Long reservationID) throws EntityNotFoundException {
        try {
            reservationDao.deleteReservation(reservationID);
        } catch (DaoException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
