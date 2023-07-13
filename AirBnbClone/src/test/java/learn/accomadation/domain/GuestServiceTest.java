package learn.accomadation.domain;

import learn.accomadation.data.DataAccessException;
import learn.accomadation.data.GuestRepository;
import learn.accomadation.data.GuestRepositoryDouble;
import learn.accomadation.models.Guest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {

    private GuestRepository repository = new GuestRepositoryDouble();
    private GuestService service = new GuestService(repository);

    @Test
    public void shouldFindByEmail() throws DataAccessException {
        Guest guest = service.findByEmail("slomas0@mediafire.com");
        assertNotNull(guest);
        assertEquals("Sullivan", guest.getFirstName());
    }

    @Test
    public void shouldFindById() throws DataAccessException {
        Guest guest = service.findById(1);
        assertNotNull(guest);
        assertEquals("Sullivan", guest.getFirstName());
    }
}