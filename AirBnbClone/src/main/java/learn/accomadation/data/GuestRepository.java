package learn.accomadation.data;

import learn.accomadation.models.Guest;

public interface GuestRepository {
    Guest findByEmail(String email) throws DataAccessException;
    Guest findById(int id) throws DataAccessException;
}
