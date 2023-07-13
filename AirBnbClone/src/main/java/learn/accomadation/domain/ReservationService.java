package learn.accomadation.domain;

import learn.accomadation.data.DataAccessException;
import learn.accomadation.data.GuestRepository;
import learn.accomadation.data.HostRepository;
import learn.accomadation.data.ReservationRepository;
import learn.accomadation.models.Guest;
import learn.accomadation.models.Host;
import learn.accomadation.models.Reservation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    //set up total in this in this layer
    private ReservationRepository reservationRepository;
    private GuestRepository guestRepository;
    private HostRepository hostRepository;

    public ReservationService(ReservationRepository reservationRepository, GuestRepository guestRepository, HostRepository hostRepository) {
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
    }

    public List<Reservation> findByHost(Host host) throws DataAccessException {
        return reservationRepository.findReservationsByHost(host);
    }

    public List<Reservation> findByHostAndGuest(Host host, Guest guest) throws DataAccessException {
        if (host == null || guest == null){
            return null;
        }
        List<Reservation> reservationsByHost = reservationRepository.findReservationsByHost(host);

        List<Reservation> reservationsByHostAndGuest = reservationsByHost.stream().filter(g -> g.getGuest().getEmail().equals(guest.getEmail())).toList();

        return reservationsByHostAndGuest;
    }

    public Result<Reservation> make(Reservation reservation) throws DataAccessException {
        Result<Reservation> result = validateNulls(reservation);
        if (!result.isSuccess()){
            return result;
        }

        result = validateGuestAndHostExist(reservation);
        if(!result.isSuccess()){
            return result;
        }

        result = validateStartBeforeEnd(reservation);
        if(!result.isSuccess()){
            return result;
        }

        result = validateOverlap(reservation);
        if(!result.isSuccess()){
            return result;
        }

        //validate start date is in the future
        if (reservation.getStart().isBefore(LocalDate.now())){
            result.addErrorMessage("Must make a reservation with a future start date.");
            return result;
        }

        //set total, loop from start date to end date, if day is weekend then weekend rate, else weekday rate
        /*
            Consider 2021-10-12 Start Date, 2021-10-14 End date

            comparing start to end will give us a negative value starting with -2. It will be 0 once the
            start date matches the end date.

            If their stay reaches 0/end date, we don't want to charge them for that day

            Friday and Saturday are weekends
         */

        LocalDate date = reservation.getStart();
        Host host = reservation.getHost();
        reservation.setTotal(BigDecimal.ZERO);

        while (date.compareTo(reservation.getEnd()) < 0) {
            if (date.getDayOfWeek() == DayOfWeek.FRIDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY){
                reservation.setTotal(reservation.getTotal().add(host.getWeekendRate()));
                date = date.plusDays(1);
            } else {
                reservation.setTotal(reservation.getTotal().add(host.getStandardRate()));
                date = date.plusDays(1);
            }
        }

        //set reservation to reservationRepository.add(reservation)
        //reservationRepository.make(reservation);
        //set payload with result.setPayload(reservation)
        result.setPayload(reservation);

        return result;
    }

    public void confirmMake(Reservation reservation) throws DataAccessException {
        reservationRepository.make(reservation);
    }

    public Result<Reservation> edit(Reservation reservation, Reservation previousReservation) throws DataAccessException {
        Result <Reservation> result = new Result<>();
        if (previousReservation.getStart().isBefore(LocalDate.now()) && previousReservation.getEnd().isBefore(LocalDate.now())){
            result.addErrorMessage("Can't edit reservations in the past");
            return result;
        }

        if (previousReservation.getStart().isBefore(LocalDate.now()) && reservation.getStart().isAfter(LocalDate.now())){
            result.addErrorMessage("Can't edit start date if reservation has already started");
            return result;
        }


        result = validateNulls(reservation);
        if (!result.isSuccess()){
            return result;
        }

        result = validateGuestAndHostExist(reservation);
        if(!result.isSuccess()){
            return result;
        }

        result = validateStartBeforeEnd(reservation);
        if(!result.isSuccess()){
            return result;
        }

        result = validateOverlap(reservation);
        if(!result.isSuccess()){
            return result;
        }


//        boolean edit = reservationRepository.edit(reservation);
//        if (!edit) {
//            result.addErrorMessage("Could not find the id of reservation to update");
//        }

        LocalDate date = reservation.getStart();
        Host host = reservation.getHost();
        reservation.setTotal(BigDecimal.ZERO);

        while (date.compareTo(reservation.getEnd()) < 0) {
            if (date.getDayOfWeek() == DayOfWeek.FRIDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY){
                reservation.setTotal(reservation.getTotal().add(host.getWeekendRate()));
                date = date.plusDays(1);
            } else {
                reservation.setTotal(reservation.getTotal().add(host.getStandardRate()));
                date = date.plusDays(1);
            }
        }

        return result;
    }

    public void confirmEdit(Reservation reservation) throws DataAccessException {
        reservationRepository.edit(reservation);
    }

    public Result<Reservation> cancel(Reservation reservation) throws DataAccessException {
        Result<Reservation> result = new Result<>();
        if (reservation.getStart().isBefore(LocalDate.now())){
            result.addErrorMessage("Cannot cancel reservation in the past");
            return result;
        }

        boolean cancel = reservationRepository.cancel(reservation);
        if(!cancel) {
            result.addErrorMessage("Could not find the id of reservation to cancel");
            return result;
        }

        return result;
    }

    //helper validation functions
    private Result<Reservation> validateNulls(Reservation reservation){
        Result<Reservation> result = new Result<>();
        
        if (reservation == null){
            result.addErrorMessage("Reservation can't be null.");
            return result;
        }
        
        if (reservation.getGuest() == null){
            result.addErrorMessage("Guest is required.");
        }
        if (reservation.getHost() == null){
            result.addErrorMessage("Host is required.");
        }
        if (reservation.getStart() == null){
            result.addErrorMessage("Start date is required.");
        }
        if (reservation.getEnd() == null){
            result.addErrorMessage("End date is required.");
        }

        return result;
    }

    private Result<Reservation> validateGuestAndHostExist(Reservation reservation) throws DataAccessException {
        Result<Reservation> result = new Result<>();
        if (hostRepository.findByEmail(reservation.getHost().getEmail()) == null) { //if host not found in hostRepository
            result.addErrorMessage("Host does not exist.");
        }

        if (guestRepository.findByEmail(reservation.getGuest().getEmail()) == null) {
            result.addErrorMessage("Guest does not exist.");
        }
        return result;
    }

    private Result<Reservation> validateStartBeforeEnd(Reservation reservation){
        Result<Reservation> result = new Result<>();
        if (reservation.getStart().isAfter(reservation.getEnd())){
            result.addErrorMessage("Start date must come before the end date.");
        }
        return result;
    }

    private Result<Reservation> validateOverlap(Reservation reservation) throws DataAccessException {
        List<Reservation> reservations = findByHost(reservation.getHost());
        Result<Reservation> result = new Result<>();

        for (Reservation r : reservations){
            if (r.getId() == reservation.getId()){ //ignores comparing the same reservation
                continue;
            }

            if(r.getEnd().isAfter(reservation.getStart()) && reservation.getEnd().isAfter(r.getStart())){
                result.addErrorMessage("Reservations cannot overlap.");
                return result;
            }
        }
        return result;
    }


}
