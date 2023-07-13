package learn.accomadation.domain;

import learn.accomadation.data.DataAccessException;
import learn.accomadation.data.HostFileRepository;
import learn.accomadation.data.HostRepository;
import learn.accomadation.data.HostRepositoryDouble;
import learn.accomadation.models.Host;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceTest {
    private HostRepository repository = new HostRepositoryDouble();
    private HostService service = new HostService(repository);

    @Test
    public void shouldFindByEmail() throws DataAccessException {
        Host host = repository.findByEmail("kdeclerkdc@sitemeter.com");
        assertNotNull(host);
    }
}