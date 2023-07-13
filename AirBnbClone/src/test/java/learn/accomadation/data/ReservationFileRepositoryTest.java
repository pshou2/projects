package learn.accomadation.data;

import learn.accomadation.models.Host;
import learn.accomadation.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {
    //file in seed
    //2e72f86c-b8fe-4265-b4f1-304dea8762db kdeclerkdc@sitemeter.com

    static final String SEED_FILE_PATH = "./data/reservations-seed/2e72f86c-b8fe-4265-b4f1-304dea8762db.csv";
    static final String TEST_FILE_PATH = "./data/reservations-test/2e72f86c-b8fe-4265-b4f1-304dea8762db.csv";
    static final String TEST_DIRECTORY_PATH = "./data/reservations-test";
    GuestFileRepository guestRepository = new GuestFileRepository("./data/guests.csv");
    HostFileRepository hostRepository = new HostFileRepository("./data/hosts.csv");
    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIRECTORY_PATH, guestRepository, hostRepository);

    @BeforeEach
    public void setUp() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    public void shouldFindReservationsByHost() throws DataAccessException {
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        List<Reservation> reservations = repository.findReservationsByHost(host);
        assert(reservations.size() > 0);

//        for (Reservation r : reservations){
//            System.out.printf("%s, Guest:%s, Host:%s, Start:%s, End:%s, Total:%s%n", r.getId(), r.getGuest(), r.getHost(),r.getStart(),r.getEnd(),r.getTotal());
//        }
    }

    //happy path
    @Test
    public void shouldMake() throws DataAccessException {
        Reservation reservation = new Reservation(
                0,
                guestRepository.findById(1),
                hostRepository.findByEmail("kdeclerkdc@sitemeter.com"),
                LocalDate.of(2023, 12, 3),
                LocalDate.of(2023, 12, 6),
                new BigDecimal(600)
        );

        Reservation actual = repository.make(reservation);
        assertNotNull(actual);
    }

    //unhappy path
    @Test
    public void shouldNotMakeNull() throws DataAccessException {
        Reservation reservation = null;
        Reservation actual = repository.make(reservation);

        assertNull(actual);
    }

    @Test
    public void shouldEditReservation() throws DataAccessException {
        //7,2021-04-05,2021-04-08,850,600
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        List<Reservation> reservations = repository.findReservationsByHost(host);
        Reservation reservation = reservations.get(0);
        reservation.setStart(LocalDate.of(2023, 12, 3));
        reservation.setEnd(LocalDate.of(2023, 12, 10));
        boolean actual = repository.edit(reservation);
        assertTrue(actual);
    }

    @Test
    public void shouldNotEditNullReservation() throws DataAccessException {
        Reservation reservation = null;
        boolean actual = repository.edit(reservation);
        assertFalse(actual);
    }

    @Test
    public void shouldCancelReservation() throws DataAccessException {
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        List<Reservation> reservations = repository.findReservationsByHost(host);
        Reservation reservation = reservations.get(0);
        boolean actual = repository.cancel(reservation);
        assertTrue(actual);
    }

    @Test
    public void shouldNotCancelNull() throws DataAccessException {
        Reservation reservation = null;
        boolean actual = repository.cancel(reservation);
        assertFalse(actual);
    }
}