package learn.accomadation.data;

import learn.accomadation.models.Guest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {
    private GuestFileRepository repository = new GuestFileRepository("./data/guests.csv");

//    @Test
//    public void shouldFindAll() throws DataAccessException {
//        List<Guest> all = repository.findAll();
//
//        for(Guest guest : all) {
//            System.out.printf("%s %s %s %s %s %s%n", guest.getId(),guest.getFirstName(),guest.getLastName(), guest.getEmail(), guest.getPhone(), guest.getState());
//        }
//    }

    @Test
    public void shouldFindByEmail() throws DataAccessException {
        String email = "bseppey4@yahoo.com";

        Guest guest = repository.findByEmail(email);
        assertNotNull(guest);
        //5,Berta,Seppey,bseppey4@yahoo.com,(202) 2668098,DC
        assertEquals(5, guest.getId());
        assertEquals("Berta", guest.getFirstName());
        assertEquals("Seppey", guest.getLastName());
        assertEquals("(202) 2668098", guest.getPhone());
        assertEquals("DC", guest.getState());
    }

    @Test
    public void shouldNotFindNullEmail() throws DataAccessException {
        String email = null;
        Guest guest = repository.findByEmail(email);
        assertNull(guest);
    }

    @Test
    public void shouldNotFindEmptyEmail() throws DataAccessException {
        String email = " ";
        Guest guest = repository.findByEmail(email);
        assertNull(guest);
    }

    @Test
    public void shouldNotFindNonExistingEmail() throws DataAccessException {
        String email = "philshou@gmail.com";
        Guest guest = repository.findByEmail(email);
        assertNull(guest);
    }
}