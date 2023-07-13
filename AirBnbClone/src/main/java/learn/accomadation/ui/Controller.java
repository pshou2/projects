package learn.accomadation.ui;

import learn.accomadation.data.DataAccessException;
import learn.accomadation.domain.GuestService;
import learn.accomadation.domain.HostService;
import learn.accomadation.domain.ReservationService;
import learn.accomadation.domain.Result;
import learn.accomadation.models.Guest;
import learn.accomadation.models.Host;
import learn.accomadation.models.Reservation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class Controller {
    private View view;
    private GuestService guestService;
    private HostService hostService;
    private ReservationService reservationService;

    public Controller(View view, GuestService guestService, HostService hostService, ReservationService reservationService) {
        this.view = view;
        this.guestService = guestService;
        this.hostService = hostService;
        this.reservationService = reservationService;
    }

    public void run(){
        view.displayHeader("Welcome to \"Don't Wreck My House\"");

        //runAppLoop();
        try {
            runAppLoop();
        } catch (DataAccessException ex){
            view.displayException(ex);
        }
    }

    private void runAppLoop() throws DataAccessException {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case VIEW:
                    view();
                    break;
                case MAKE:
                    make();
                    break;
                case EDIT:
                    edit();
                    break;
                case CANCEL:
                    cancel();
                    break;
            }
        } while (option != MainMenuOption.EXIT);
    }

    private void view() throws DataAccessException {
        view.displayHeader("View Reservations for Host");
        String email = view.getEmail("Host Email: ");
        Host host = hostService.findByEmail(email);
        List<Reservation> reservationsByHost = reservationService.findByHost(host);

        view.displayReservations(reservationsByHost);
    }

    private void make() throws DataAccessException {
        view.displayHeader("Make a Reservation");
        Guest guest = guestService.findByEmail(view.getEmail("Guest Email: "));
        Host host = hostService.findByEmail(view.getEmail("Host Email: "));
        List<Reservation> reservationsByHost = reservationService.findByHost(host);
        view.displayReservations(reservationsByHost);

        Reservation reservation = view.makeReservation(guest, host);
        Result<Reservation> result = reservationService.make(reservation);


        if (!result.isSuccess()) {
            view.displayErrors(result.getErrorMessages());
        } else {
            BigDecimal total = result.getPayload().getTotal();
            view.displaySummary(reservation,total);
            if (view.getConfirmation("Confirm [y/n]: ")){
                reservationService.confirmMake(result.getPayload());
                view.display(String.format("Reservation %s has been made%n", result.getPayload().getId()));
            } else {
                view.display("Reservation not made");
            }
        }
    }

    private void edit() throws DataAccessException {
        view.displayHeader("Edit a Reservation");
        Guest guest = guestService.findByEmail(view.getEmail("Guest Email: "));
        Host host = hostService.findByEmail(view.getEmail("Host Email: "));
        List<Reservation> reservationsByHostAndGuest = reservationService.findByHostAndGuest(host, guest);

        if (reservationsByHostAndGuest == null){
            view.displayReservations(reservationsByHostAndGuest);
        } else {
            view.displayReservations(reservationsByHostAndGuest);
            Reservation reservation = view.chooseReservation(reservationsByHostAndGuest);
            view.displayHeader("Editing Reservation " + reservation.getId());
            Reservation editReservation = view.editReservation(reservation);
            Result<Reservation> result = reservationService.edit(editReservation, reservation);

            if (!result.isSuccess()) {
                view.displayErrors(result.getErrorMessages());
            } else {
                BigDecimal total = editReservation.getTotal();
                view.displaySummary(editReservation, total);
                if (view.getConfirmation("Confirm [y/n]: ")){
                    reservationService.confirmEdit(editReservation);
                    view.displayHeader("Success");
                    view.display(String.format("Reservation %s has been updated%n", editReservation.getId()));
                } else {
                    view.display("Reservation not updated");
                }
            }
        }
    }

    private void cancel() throws DataAccessException {
        view.displayHeader("Cancel a Reservation");
        Guest guest = guestService.findByEmail(view.getEmail("Guest Email: "));
        Host host = hostService.findByEmail(view.getEmail("Host Email: "));
        List<Reservation> reservationsByHostAndGuest = reservationService.findByHostAndGuest(host, guest);

        if (reservationsByHostAndGuest == null){
            view.displayReservations(reservationsByHostAndGuest);
        } else {
            view.displayReservations(reservationsByHostAndGuest);
            Reservation reservation = view.chooseReservation(reservationsByHostAndGuest);
            Result<Reservation> result = reservationService.cancel(reservation);

            if (!result.isSuccess()) {
                view.displayErrors(result.getErrorMessages());
            } else {
                view.displayHeader("Success");
                view.display("Reservation " + reservation.getId() +  " has been cancelled.");
            }
        }

    }
}
