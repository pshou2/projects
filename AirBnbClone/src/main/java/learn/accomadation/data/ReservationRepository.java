package learn.accomadation.data;

import learn.accomadation.models.Host;
import learn.accomadation.models.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findReservationsByHost(Host host) throws DataAccessException;

    Reservation make(Reservation reservation) throws DataAccessException;

    boolean edit(Reservation reservation) throws DataAccessException;

    boolean cancel(Reservation reservation) throws DataAccessException;
}
