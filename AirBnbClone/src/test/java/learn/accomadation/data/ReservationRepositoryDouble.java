package learn.accomadation.data;

import learn.accomadation.models.Guest;
import learn.accomadation.models.Host;
import learn.accomadation.models.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepositoryDouble implements ReservationRepository{
    private List<Reservation> result = new ArrayList<>();

    public ReservationRepositoryDouble(){
        Reservation res1 = new Reservation();
        Host host1 = new Host();
        Guest guest1 = new Guest();
        host1.setEmail("kdeclerkdc@sitemeter.com");
        guest1.setEmail("slomas0@mediafire.com");
        res1.setId(1);
        res1.setHost(host1);
        res1.setGuest(guest1);
        res1.setStart(LocalDate.of(2024, 10, 12));
        res1.setEnd(LocalDate.of(2024, 10, 15));

        Reservation res2 = new Reservation();
        Host host2 = new Host();
        Guest guest2 = new Guest();
        host2.setEmail("kdeclerkdc@sitemeter.com");
        guest2.setEmail("slomas0@mediafire.com");
        res2.setId(2);
        res2.setHost(host2);
        res2.setGuest(guest2);
        res2.setStart(LocalDate.of(2021, 9, 10));
        res2.setEnd(LocalDate.of(2021, 9, 16));

        Reservation res3 = new Reservation();
        Host host3 = new Host();
        Guest guest3 = new Guest();
        host3.setEmail("kdeclerkdc@sitemeter.com");
        guest3.setEmail("slomas0@mediafire.com");
        res3.setId(3);
        res3.setHost(host3);
        res3.setGuest(guest3);
        res3.setStart(LocalDate.of(2021, 10, 2));
        res3.setEnd(LocalDate.of(2021, 10, 4));

        Reservation res4 = new Reservation();
        Host host4 = new Host();
        Guest guest4 = new Guest();
        host4.setEmail("kdeclerkdc@sitemeter.com");
        guest4.setEmail("ogecks1@dagondesign.com");
        res4.setId(4);
        res4.setHost(host4);
        res4.setGuest(guest4);
        res4.setStart(LocalDate.of(2021, 1, 2));
        res4.setEnd(LocalDate.of(2021, 1, 4));
        result.add(res1);
        result.add(res2);
        result.add(res3);
        result.add(res4);
    }
    @Override
    public List<Reservation> findReservationsByHost(Host host) throws DataAccessException {
        /*
        2e72f86c-b8fe-4265-b4f1-304dea8762db host id

        id,start_date,end_date,guest_id,total
        1,2021-10-12,2021-10-14,663,400
        2,2021-09-10,2021-09-16,136,1300
        3,2021-10-02,2021-10-04,738,450

         */
        return result;
    }

    @Override
    public Reservation make(Reservation reservation) throws DataAccessException {
        return reservation;
    }

    @Override
    public boolean edit(Reservation reservation) throws DataAccessException {
        return reservation.getId() != 999;
    }

    @Override
    public boolean cancel(Reservation reservation) throws DataAccessException {
        return reservation.getId() != 999;
    }

}
