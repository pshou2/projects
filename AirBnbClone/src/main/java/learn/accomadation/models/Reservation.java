package learn.accomadation.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reservation {
    private int id;
    private Guest guest;
    private Host host;
    private LocalDate start;
    private LocalDate end;
    private BigDecimal total;

    public Reservation(int id, Guest guest, Host host, LocalDate start, LocalDate end, BigDecimal total) {
        this.id = id;
        this.guest = guest;
        this.host = host;
        this.start = start;
        this.end = end;
        this.total = total;
    }

    //Empty Constructor
    public Reservation(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
