package learn.accomadation.ui;

import com.sun.tools.javac.Main;
import learn.accomadation.models.Guest;
import learn.accomadation.models.Host;
import learn.accomadation.models.Reservation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Component
public class View {
    private ConsoleIO io;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public View(ConsoleIO io) {
        this.io = io;
    }

    public MainMenuOption selectMainMenuOption () {
        displayHeader("Main Menu");
        for (MainMenuOption option : MainMenuOption.values()){
            io.printf("%s. %s%n", option.getValue(), option.getMessage());
        }

        String message = String.format("Select [%s-%s]: ", 1, 5);
        return MainMenuOption.fromValue(io.readInt(message, 1, 5));
    }

    public Reservation makeReservation(Guest guest, Host host){
        Reservation reservation = new Reservation();
        reservation.setId(0);
        reservation.setGuest(guest);
        reservation.setHost(host);
        reservation.setStart(getDate("Start (mm/dd/yyyy): "));
        reservation.setEnd(getDate("End (mm/dd/yyyy): "));
        reservation.setTotal(BigDecimal.ZERO);
        return reservation;
    }

    public Reservation editReservation(Reservation reservation){
        Reservation res = new Reservation();
        res.setId(reservation.getId());
        res.setGuest(reservation.getGuest());
        res.setHost(reservation.getHost());
        res.setStart(getDate(String.format("Start (%s): ", reservation.getStart().format(formatter))));
        res.setEnd(getDate(String.format("End (%s): ", reservation.getEnd().format(formatter))));
        res.setTotal(BigDecimal.ZERO);
        return res;
    }

    public Reservation chooseReservation(List<Reservation> reservations){
        while(true) {
            int id = io.readPosInt("Reservation ID: ");
            for (Reservation r : reservations) {
                if (r.getId() == id) {
                    return r;
                }
            }
            io.println("Reservation ID was not found, input another id.");
        }
    }
    public String getEmail(String prompt) {
        return io.readRequiredString(prompt);
    }

    public LocalDate getDate(String prompt){

        return io.readLocalDate(prompt);
    }

    public boolean getConfirmation(String prompt) {
        return io.readBoolean(prompt);
    }

    public void display(String message) {
        io.println(message);
    }

    public void displaySummary(Reservation reservation, BigDecimal total) {
        displayHeader("Summary");
        display("Start: " + reservation.getStart());
        display("End: " + reservation.getEnd());
        displayTotal(total);
    }

    public void displayTotal(BigDecimal total){
        io.printf("Total: %s%n", total);
    }

    public void displayErrors(List<String> errors) {
        for (String e : errors) {
            io.println(e);
        }
    }

    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }

    public void displayException(Exception ex) {
        displayHeader("A critical error occurred:");
        io.println(ex.getMessage());
    }

    public void displayReservations(List<Reservation> reservations) {
        if (reservations == null) {
            io.println("Host, guest, or both do not exist.");
        } else if (reservations.size() == 0){
            io.println("No reservations with this host.");
        }
        else {
            List<Reservation> sorted = reservations.stream()
                    .sorted(Comparator.comparing(Reservation::getStart))
                    .toList();

            Host host = sorted.get(0).getHost();
            displayHeader(String.format("%s: %s, %s", host.getLastName(), host.getCity(), host.getState()));
            for (Reservation r : sorted) {
                String formatStart = r.getStart().format(formatter);
                String formatEnd = r.getEnd().format(formatter);
                io.printf("ID: %s, %s - %s, Guest: %s, %s, Email: %s%n", r.getId(), formatStart, formatEnd, r.getGuest().getLastName(), r.getGuest().getFirstName(), r.getGuest().getEmail());
            }
        }
    }
}
