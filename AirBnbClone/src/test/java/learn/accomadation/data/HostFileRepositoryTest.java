package learn.accomadation.data;

import learn.accomadation.models.Guest;
import learn.accomadation.models.Host;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {
    private HostFileRepository repository = new HostFileRepository("./data/hosts.csv");
//    @Test
//    public void shouldFindAll() throws DataAccessException {
//        List<Host> all = repository.findAll();
//
//        for(Host host : all) {
//            System.out.printf("%s %s %s %s %s %s %s %s %s %s%n",host.getId(), host.getLastName(), host.getEmail(), host.getPhone(), host.getAddress(), host.getCity(), host.getState(), host.getZipCode(), host.getStandardRate(), host.getWeekendRate());
//        }
//    }

    @Test
    public void shouldFindByEmail() throws DataAccessException {
        String email = "rspellesy3@google.co.jp";
        Host host = repository.findByEmail(email);
        assertNotNull(host);
    }
}