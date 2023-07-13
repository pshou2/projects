package learn.accomadation.data;

import learn.accomadation.models.Guest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
@Repository
public class GuestFileRepository implements GuestRepository {
    private String filepath;
    private final String delimiter = ",";

    public GuestFileRepository(@Value("${guestDataFilePath}") String filepath) {
        this.filepath = filepath;
    }

    @Override
    public Guest findByEmail(String email) throws DataAccessException {
        List<Guest> all = findAll();
        Guest guest = null;

        for (Guest g : all) {
            if (g.getEmail().equals(email)){
                guest = g;
                return guest;
            }
        }

        return guest;
    }

    @Override
    public Guest findById(int id) throws DataAccessException {
        List<Guest> all = findAll();
        Guest guest = null;

        for (Guest g : all) {
            if(g.getId() == id){
                guest = g;
                return guest;
            }
        }
        return guest;
    }

    private List<Guest> findAll() throws DataAccessException {
        ArrayList<Guest> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            reader.readLine();

            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                Guest guest = lineToGuest(line);
                result.add(guest);
            }
        }  catch (FileNotFoundException e) {
            //
        } catch (IOException e) {
            throw new DataAccessException("Could not open file " + filepath, e);
        }
        return result;
    }

    private Guest lineToGuest(String line){
        String[] fields = line.split(delimiter);
        if (fields.length != 6){
            return null;
        }
        return new Guest(
            Integer.parseInt(fields[0]),
            fields[1],
            fields[2],
            fields[3],
            fields[4],
            fields[5]
        );
    }
}
