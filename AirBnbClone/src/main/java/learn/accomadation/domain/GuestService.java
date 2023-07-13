package learn.accomadation.domain;

import learn.accomadation.data.DataAccessException;
import learn.accomadation.data.GuestRepository;
import learn.accomadation.models.Guest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GuestService {
    private GuestRepository repository;

    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

    public Guest findByEmail(String email) throws DataAccessException {
        return repository.findByEmail(email);
    }

    public Guest findById(int id) throws DataAccessException {
        return repository.findById(id);
    }
}
