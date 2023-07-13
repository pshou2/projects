package learn.accomadation.data;

import learn.accomadation.models.Guest;

public class GuestRepositoryDouble implements GuestRepository{
    @Override
    public Guest findByEmail(String email) throws DataAccessException {
        if (email.equals("slomas0@mediafire.com")){
            return new Guest(1,
                    "Sullivan",
                    "Lomas",
                    "slomas0@mediafire.com",
                    "(702) 7768761",
                    "NV");
        }
        return null;
    }

    @Override
    public Guest findById(int id) throws DataAccessException {
        return new Guest(1,
                "Sullivan",
                "Lomas",
                "slomas0@mediafire.com",
                "(702) 7768761",
                "NV");
    }
}
