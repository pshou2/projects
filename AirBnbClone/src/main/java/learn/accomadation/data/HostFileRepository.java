package learn.accomadation.data;

import learn.accomadation.models.Guest;
import learn.accomadation.models.Host;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HostFileRepository implements HostRepository {
    private String filepath;
    private final String delimiter = ",";

    public HostFileRepository(@Value("${hostDataFilePath}") String filepath) {

        this.filepath = filepath;
    }
    
    @Override
    public Host findByEmail(String email) throws DataAccessException {
        List<Host> all = findAll();
        Host host = null;

        for (Host h : all) {
            if (h.getEmail().equals(email)){
                host = h;
                return host;
            }
        }
        return host;
    }

    private List<Host> findAll() throws DataAccessException {
        ArrayList<Host> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            reader.readLine();

            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                Host guest = lineToHost(line);
                result.add(guest);
            }
        }  catch (FileNotFoundException e) {
            //
        } catch (IOException e) {
            throw new DataAccessException("Could not open file " + filepath, e);
        }
        return result;
    }

    private Host lineToHost(String line) {
        String[] fields = line.split(delimiter);
        if (fields.length != 10) {
            return null;
        }
        return new Host(
                fields[0], //id
                fields[1], //lastName
                fields[2], //email
                fields[3], //phone
                fields[4], //address
                fields[5], //city
                fields[6], //state
                Integer.parseInt(fields[7]), //zipcode
                new BigDecimal(fields[8]), //standardRate
                new BigDecimal(fields[9]) //weekendRate
        );
    }
}
