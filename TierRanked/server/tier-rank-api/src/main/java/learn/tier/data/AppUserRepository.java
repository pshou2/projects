package learn.tier.data;

import learn.tier.models.AppUser;
import java.util.List;

public interface AppUserRepository {
    public List<AppUser> findAll();
    AppUser findByUsername(String username);
    AppUser create(AppUser user);
    boolean update(AppUser user);
}
