package learn.accomadation.data;

import learn.accomadation.models.Host;
import learn.accomadation.models.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationFileRepository implements ReservationRepository {
    private String directory;
    private GuestRepository guestRepository;
    private HostRepository hostRepository;
    private final String DELIMITER = ",";
    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    // Dates are in Year Month Day
    //private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ReservationFileRepository(@Value("${reservationFilePath}") String directory, GuestRepository guestRepository, HostRepository hostRepository) {
        this.directory = directory;
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
    }

    @Override
    public List<Reservation> findReservationsByHost(Host host) throws DataAccessException {
        //check if host is null, host id is null, email is null

        ArrayList<Reservation> result = new ArrayList<>();

        if (host == null){
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(host.getId(), directory)))){

            reader.readLine();

            for(String line = reader.readLine(); line != null; line = reader.readLine()){
                Reservation reservation = lineToReservation(line, host);
                result.add(reservation);
            }

        }  catch (IOException e) {
            //
        }

        if (result.size() == 0) {

        }
        return result;
    }

    @Override
    public Reservation make(Reservation reservation) throws DataAccessException {
        if (reservation == null){
            return null;
        }

        List<Reservation> all = findReservationsByHost(reservation.getHost());
        reservation.setId(getNextId(all));
        all.add(reservation);
        writeAll(all, reservation.getHost().getId());
        return reservation;
    }

    @Override
    public boolean edit(Reservation reservation) throws DataAccessException {
        if (reservation == null){
            return false;
        }

        List<Reservation> reservations = findReservationsByHost(reservation.getHost());

        for (int i = 0; i < reservations.size(); i++){
            if(reservations.get(i).getId() == reservation.getId()) {
                reservations.set(i, reservation);
                writeAll(reservations, reservation.getHost().getId());
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean cancel(Reservation reservation) throws DataAccessException {
        if (reservation == null){
            return false;
        }

        List<Reservation> reservations = findReservationsByHost(reservation.getHost());

        for (int i = 0; i < reservations.size(); i++){
            if(reservations.get(i).getId() == reservation.getId()) {
                reservations.remove(i);
                writeAll(reservations, reservation.getHost().getId());
                return true;
            }
        }

        return false;
    }

    //Helper methods
    private String getFilePath(String hostId, String directory) {
        //return String.format("./data/%s/%s.csv",directory,email); alternative?
        return Paths.get(directory, hostId + ".csv").toString();
    }

    private void writeAll(List<Reservation> reservations, String hostId) throws DataAccessException {
        try (PrintWriter writer = new PrintWriter(getFilePath(hostId, directory))){
            writer.println(HEADER);

            for (Reservation r : reservations) {
                writer.println(reservationToLine(r));
            }
        } catch (FileNotFoundException e) {
            throw new DataAccessException(e);
        }
    }


    private Reservation lineToReservation(String line, Host host) throws DataAccessException {
        String[] fields = line.split(DELIMITER);
        if (fields.length != 5) {
            return null;
        }

        return new Reservation(
                Integer.parseInt(fields[0]),
                guestRepository.findById(Integer.parseInt(fields[3])), //guest
                host, //host
                LocalDate.parse(fields[1]), //start date
                LocalDate.parse(fields[2]), //end date
                new BigDecimal(fields[4])
        );
    }

    private String reservationToLine(Reservation reservation){
        //id,start_date,end_date,guest_id,total
        return String.format("%s,%s,%s,%s,%s",
                reservation.getId(),
                reservation.getStart(),
                reservation.getEnd(),
                reservation.getGuest().getId(),
                reservation.getTotal());
    }

    private int getNextId(List<Reservation> reservations){
        int maxId = 0;
        for(Reservation r : reservations){
            if(maxId < r.getId()){
                maxId = r.getId();
            }
        }
        return maxId+1;
    }
}
