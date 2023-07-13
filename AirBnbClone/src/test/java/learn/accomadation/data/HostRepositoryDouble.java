package learn.accomadation.data;

import learn.accomadation.models.Host;

import java.math.BigDecimal;

public class HostRepositoryDouble implements HostRepository{
    @Override
    public Host findByEmail(String email) throws DataAccessException {
        //2e72f86c-b8fe-4265-b4f1-304dea8762db,de Clerk,kdeclerkdc@sitemeter.com,(208) 9496329,2 Debra Way,Boise,ID,83757,200,250
        if (email.equals("kdeclerkdc@sitemeter.com")){
            return new Host("2e72f86c-b8fe-4265-b4f1-304dea8762db",
                    "de Clerk",
                    "kdeclerkdc@sitemeter.com",
                    "(208) 9496329",
                    "2 Debra Way",
                    "Boise",
                    "ID",
                    83757,
                    new BigDecimal(200),
                    new BigDecimal(250));
        }
        return null;
    }
}
