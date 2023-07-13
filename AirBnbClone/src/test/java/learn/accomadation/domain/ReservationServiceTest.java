package learn.accomadation.domain;

import learn.accomadation.data.*;
import learn.accomadation.models.Guest;
import learn.accomadation.models.Host;
import learn.accomadation.models.Reservation;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {
    private ReservationRepository reservationRepository = new ReservationRepositoryDouble();
    private GuestRepository guestRepository = new GuestRepositoryDouble();
    private HostRepository hostRepository = new HostRepositoryDouble();

    private ReservationService service = new ReservationService(reservationRepository, guestRepository, hostRepository);
    @Test
    public void shouldFindByGuestAndHost() throws DataAccessException {
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        Guest guest = guestRepository.findByEmail("slomas0@mediafire.com");
        List<Reservation> whatIsThis = service.findByHostAndGuest(host,guest);

        assert(whatIsThis.size() > 0);
        for (Reservation r : whatIsThis) {
            System.out.printf("%s %s %s %s %s %n",r.getId(),r.getHost().getEmail(),r.getGuest().getEmail(), r.getStart(), r.getEnd());
        }
    }

    @Test
    public void shouldFindByNoneNullGuest() throws DataAccessException {
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        List<Reservation> whatIsThis = service.findByHostAndGuest(host,null);


        assertNull(whatIsThis);
//        for (Reservation r : whatIsThis) {
//            System.out.printf("%s %s %s %s %s %n",r.getId(),r.getHost().getEmail(),r.getGuest().getEmail(), r.getStart(), r.getEnd());
//        }
    }

    //Happy Path
    @Test
    public void shouldMakeReservation() throws DataAccessException {
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        Guest guest = guestRepository.findByEmail("slomas0@mediafire.com");

        Reservation reservation = new Reservation();
        reservation.setId(0);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(LocalDate.of(2025, 10, 14));
        reservation.setEnd(LocalDate.of(2025, 10, 18));

        Result<Reservation> actual = service.make(reservation);
        assertNotNull(actual.getPayload());
        assertEquals(0, actual.getErrorMessages().size());
    }

    //Unhappy path
    @Test
    public void shouldNotMakeNullReservation() throws DataAccessException {
        Reservation reservation = null;
        Result<Reservation> actual = service.make(reservation);
        assertNull(actual.getPayload());
    }

    @Test
    public void shouldNotMakeNullHost() throws DataAccessException {
        Host host = null;
        Guest guest = guestRepository.findByEmail("slomas0@mediafire.com");

        Reservation reservation = new Reservation();
        reservation.setId(0);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(LocalDate.of(2024, 2, 1));
        reservation.setEnd(LocalDate.of(2024, 3, 1));

        Result<Reservation> actual = service.make(reservation);
        assertNull(actual.getPayload());
        assertEquals(1, actual.getErrorMessages().size());
    }

    @Test
    public void shouldNotMakeNullGuest() throws DataAccessException {
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        Guest guest = null;

        Reservation reservation = new Reservation();
        reservation.setId(0);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(LocalDate.of(2024, 2, 1));
        reservation.setEnd(LocalDate.of(2024, 3, 1));

        Result<Reservation> actual = service.make(reservation);
        assertNull(actual.getPayload());
        assertEquals(1, actual.getErrorMessages().size());
    }

    @Test
    public void shouldNotMakeNullStartDate() throws DataAccessException {
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        Guest guest = guestRepository.findByEmail("slomas0@mediafire.com");

        Reservation reservation = new Reservation();
        reservation.setId(0);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(null);
        reservation.setEnd(LocalDate.of(2024, 3, 1));

        Result<Reservation> actual = service.make(reservation);
        assertNull(actual.getPayload());
        assertEquals(1, actual.getErrorMessages().size());
    }

    @Test
    public void shouldNotMakeNullEndDate() throws DataAccessException {
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        Guest guest = guestRepository.findByEmail("slomas0@mediafire.com");

        Reservation reservation = new Reservation();
        reservation.setId(0);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(LocalDate.of(2024, 2, 1));
        reservation.setEnd(null);

        Result<Reservation> actual = service.make(reservation);
        assertNull(actual.getPayload());
        assertEquals(1, actual.getErrorMessages().size());
    }

    @Test
    public void shouldNotMakeNonExistingGuest() throws DataAccessException {
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        Guest guest = guestRepository.findByEmail("philshou@gmail.com");

        Reservation reservation = new Reservation();
        reservation.setId(0);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(LocalDate.of(2024, 2, 1));
        reservation.setEnd(LocalDate.of(2024, 3, 1));

        Result<Reservation> actual = service.make(reservation);
        assertNull(actual.getPayload());
        assertEquals(1, actual.getErrorMessages().size());
    }

    @Test
    public void shouldNotMakeNonExistingHost() throws DataAccessException {
        Host host = hostRepository.findByEmail("dobe@it.com");
        Guest guest = guestRepository.findByEmail("slomas0@mediafire.com");

        Reservation reservation = new Reservation();
        reservation.setId(0);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(LocalDate.of(2024, 2, 1));
        reservation.setEnd(LocalDate.of(2024, 3, 1));

        Result<Reservation> actual = service.make(reservation);
        assertNull(actual.getPayload());
        assertEquals(1, actual.getErrorMessages().size());
    }

    @Test
    public void startMustComeBeforeEnd() throws DataAccessException {
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        Guest guest = guestRepository.findByEmail("slomas0@mediafire.com");

        Reservation reservation = new Reservation();
        reservation.setId(0);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(LocalDate.of(2024, 4, 1));
        reservation.setEnd(LocalDate.of(2024, 3, 1));

        Result<Reservation> actual = service.make(reservation);
        assertNull(actual.getPayload());
        assertEquals(1, actual.getErrorMessages().size());
    }

    @Test
    public void shouldNotMakeOverlappingReservationSurrounds() throws DataAccessException {
        //2024-10-12 2024-10-15
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        Guest guest = guestRepository.findByEmail("slomas0@mediafire.com");

        Reservation reservation = new Reservation();
        reservation.setId(0);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(LocalDate.of(2024, 1, 11));
        reservation.setEnd(LocalDate.of(2024, 10, 16));

        Result<Reservation> actual = service.make(reservation);
        assertNull(actual.getPayload());
        assertEquals(1, actual.getErrorMessages().size());
    }

    @Test
    public void shouldNotMakeOverLappingInside() throws DataAccessException {
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        Guest guest = guestRepository.findByEmail("slomas0@mediafire.com");

        Reservation reservation = new Reservation();
        reservation.setId(0);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(LocalDate.of(2024, 10, 13));
        reservation.setEnd(LocalDate.of(2024, 10, 14));

        Result<Reservation> actual = service.make(reservation);
        assertNull(actual.getPayload());
        assertEquals(1, actual.getErrorMessages().size());
    }

    @Test
    public void shouldNotMakeOverLappingStart() throws DataAccessException {
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        Guest guest = guestRepository.findByEmail("slomas0@mediafire.com");

        Reservation reservation = new Reservation();
        reservation.setId(0);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(LocalDate.of(2024, 10, 1));
        reservation.setEnd(LocalDate.of(2024, 10, 14));

        Result<Reservation> actual = service.make(reservation);
        assertNull(actual.getPayload());
        assertEquals(1, actual.getErrorMessages().size());
    }

    @Test
    public void shouldNotMakeOverlappingEnd() throws DataAccessException {
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        Guest guest = guestRepository.findByEmail("slomas0@mediafire.com");

        Reservation reservation = new Reservation();
        reservation.setId(0);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(LocalDate.of(2024, 10, 13));
        reservation.setEnd(LocalDate.of(2024, 10, 18));

        Result<Reservation> actual = service.make(reservation);
        assertNull(actual.getPayload());
        assertEquals(1, actual.getErrorMessages().size());
    }

    @Test
    public void shouldNotMakePastReservation() throws DataAccessException {
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        Guest guest = guestRepository.findByEmail("slomas0@mediafire.com");

        Reservation reservation = new Reservation();
        reservation.setId(0);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(LocalDate.of(2020, 10, 13));
        reservation.setEnd(LocalDate.of(2020, 10, 14));

        Result<Reservation> actual = service.make(reservation);
        assertNull(actual.getPayload());
        assertEquals(1, actual.getErrorMessages().size());
    }

    //Happy path for edit
    /*
        1 kdeclerkdc@sitemeter.com slomas0@mediafire.com 2024-10-12 2024-10-15
        2 kdeclerkdc@sitemeter.com slomas0@mediafire.com 2021-09-10 2021-09-16
        3 kdeclerkdc@sitemeter.com slomas0@mediafire.com 2021-10-02 2021-10-04
    */
    @Test
    public void shouldEditReservation() throws DataAccessException {
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        Guest guest = guestRepository.findByEmail("slomas0@mediafire.com");

        List<Reservation> reservations = service.findByHostAndGuest(host, guest);

        Reservation reservation = reservations.get(0);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(LocalDate.of(2024, 10, 13));
        reservation.setEnd(LocalDate.of(2024, 10, 14));

        Reservation previousReservation = new Reservation();
        previousReservation.setStart(LocalDate.of(2025, 10, 13));
        previousReservation.setEnd(LocalDate.of(2025, 10, 14));

        Result<Reservation> actual = service.edit(reservation, previousReservation);
        assertEquals(0, actual.getErrorMessages().size());
    }

    @Test
    public void shouldEditEndDateReservationStarted() throws DataAccessException {
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        Guest guest = guestRepository.findByEmail("slomas0@mediafire.com");

        List<Reservation> reservations = service.findByHostAndGuest(host, guest);

        Reservation reservation = reservations.get(0);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(LocalDate.of(2023, 5, 17));
        reservation.setEnd(LocalDate.of(2024, 5, 26));

        Reservation previousReservation = new Reservation();
        previousReservation.setStart(LocalDate.of(2023, 5, 17));
        previousReservation.setEnd(LocalDate.of(2023, 5, 28));

        Result<Reservation> actual = service.edit(reservation, previousReservation);
        assertEquals(0, actual.getErrorMessages().size());
    }

    @Test
    public void shouldNotEditPastReservation() throws DataAccessException {
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        Guest guest = guestRepository.findByEmail("slomas0@mediafire.com");

        List<Reservation> reservations = service.findByHostAndGuest(host, guest);
        Reservation reservation = reservations.get(0);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(LocalDate.of(2026, 10, 13));
        reservation.setEnd(LocalDate.of(2026, 10, 14));

        Reservation previousReservation = new Reservation();
        previousReservation.setId(reservation.getId());
        previousReservation.setHost(host);
        previousReservation.setGuest(guest);
        previousReservation.setStart(LocalDate.of(2020, 10, 13));
        previousReservation.setEnd(LocalDate.of(2020, 10, 14));

        Result<Reservation> actual = service.edit(reservation, previousReservation);
        assertEquals(1, actual.getErrorMessages().size());
    }

    @Test
    public void shouldNotEditNonExistingId() throws DataAccessException {
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        Guest guest = guestRepository.findByEmail("slomas0@mediafire.com");

        Reservation reservation = new Reservation();
        reservation.setId(999);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(LocalDate.of(2026, 10, 13));
        reservation.setEnd(LocalDate.of(2026, 10, 14));

        Reservation previousReservation = new Reservation();
        previousReservation.setId(999);
        previousReservation.setHost(host);
        previousReservation.setGuest(guest);
        previousReservation.setStart(LocalDate.of(2020, 10, 13));
        previousReservation.setEnd(LocalDate.of(2020, 10, 14));

        Result<Reservation> actual = service.edit(reservation, previousReservation);
        assertEquals(1, actual.getErrorMessages().size());
    }

    @Test
    public void shouldNotEditStartDateAlreadyStarted() throws DataAccessException {
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        Guest guest = guestRepository.findByEmail("slomas0@mediafire.com");

        List<Reservation> reservations = service.findByHostAndGuest(host, guest);

        Reservation reservation = reservations.get(0);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(LocalDate.of(2023, 5, 19));
        reservation.setEnd(LocalDate.of(2024, 5, 26));

        Reservation previousReservation = new Reservation();
        previousReservation.setStart(LocalDate.of(2023, 5, 17));
        previousReservation.setEnd(LocalDate.of(2023, 5, 26));

        Result<Reservation> actual = service.edit(reservation, previousReservation);
        assertEquals(1, actual.getErrorMessages().size());
    }

    @Test
    public void shouldCancelReservation() throws DataAccessException {
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        Guest guest = guestRepository.findByEmail("slomas0@mediafire.com");

        List<Reservation> reservations = service.findByHostAndGuest(host, guest);
        Reservation reservation = reservations.get(0);
        Result<Reservation> actual = service.cancel(reservation);
        assertEquals(0, actual.getErrorMessages().size());
    }

    @Test
    public void shouldNotCancelNonExistingId() throws DataAccessException {
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        Guest guest = guestRepository.findByEmail("slomas0@mediafire.com");

        Reservation reservation = new Reservation();
        reservation.setId(999);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(LocalDate.of(2026, 10, 13));
        reservation.setEnd(LocalDate.of(2026, 10, 14));

        Result<Reservation> actual = service.cancel(reservation);
        assertEquals(1, actual.getErrorMessages().size());
    }

    @Test
    public void shouldNotCancelPastReservation() throws DataAccessException {
        Host host = hostRepository.findByEmail("kdeclerkdc@sitemeter.com");
        Guest guest = guestRepository.findByEmail("slomas0@mediafire.com");

        List<Reservation> reservations = service.findByHostAndGuest(host, guest);
        Reservation reservation = reservations.get(1);
        Result<Reservation> actual = service.cancel(reservation);
        assertEquals(1, actual.getErrorMessages().size());
    }
}