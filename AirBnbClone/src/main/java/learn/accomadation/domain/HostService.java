package learn.accomadation.domain;

import learn.accomadation.data.DataAccessException;
import learn.accomadation.data.HostRepository;
import learn.accomadation.models.Host;
import org.springframework.stereotype.Service;

@Service
public class HostService {
    private HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public Host findByEmail(String email) throws DataAccessException {
        return repository.findByEmail(email);
    }

}
