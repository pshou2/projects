package learn.tier.data;

import learn.tier.models.AppUser;
import learn.tier.models.DisplayProfile;

import java.util.List;

public interface DisplayProfileRepository {
    List<DisplayProfile> findAll();

    DisplayProfile findByUserId(int appUserId);

    DisplayProfile findByUsername(String username);

    DisplayProfile add(DisplayProfile displayProfile);

    boolean update(DisplayProfile displayProfile);

    boolean deleteById(int appUserId);

    int getUsageCount(int appUserId);
}
