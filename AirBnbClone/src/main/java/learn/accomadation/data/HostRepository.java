package learn.accomadation.data;

import learn.accomadation.models.Host;

public interface HostRepository {
    Host findByEmail(String email) throws DataAccessException;
}
