package learn.tier.domain;

import learn.tier.data.DisplayProfileRepository;
import learn.tier.models.AppUser;
import learn.tier.models.DisplayProfile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisplayProfileService {
    //fields
    private final DisplayProfileRepository repository;

    //constructor
    public DisplayProfileService(DisplayProfileRepository repository) {
        this.repository = repository;
    }

    public List<DisplayProfile> findAll(){
        return repository.findAll();
    }
    public DisplayProfile findByUserId(int appUserId){
        return repository.findByUserId(appUserId);
    }

    public DisplayProfile findByUsername(String username){
        return repository.findByUsername(username);
    }

    public Result<DisplayProfile> add(DisplayProfile displayProfile){
        Result<DisplayProfile> result = validate(displayProfile);
        if (!result.isSuccess()){
            return result;
        }

        if (displayProfile.getDisplayProfileId() != 0){
            result.addMessage("display_profile_id must not be set for 'add' operation.", ResultType.INVALID);
            return result;
        }

        if(repository.getUsageCount(displayProfile.getAppUserId()) != 0) {
            result.addMessage("User already has a profile.", ResultType.INVALID);
            return result;
        }

        displayProfile = repository.add(displayProfile);
        result.setPayload(displayProfile);
        return result;
    }

    public Result<DisplayProfile> update(DisplayProfile displayProfile) {
        Result<DisplayProfile> result = validate(displayProfile);
        if (!result.isSuccess()){
            return result;
        }
        if (displayProfile.getDisplayProfileId() <= 0){
            result.addMessage("display_profile_id must be set for 'update' operation.", ResultType.INVALID);
            return result;
        }

        if(!repository.update(displayProfile)){
            String msg = String.format("app_user_id: %s was not found", displayProfile.getAppUserId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public Result<DisplayProfile> deleteById(int appUserId){
        Result<DisplayProfile> result = new Result<DisplayProfile>();
        if(!repository.deleteById(appUserId)){
            result.addMessage("app_user_id not found",ResultType.NOT_FOUND);
        }
        return result;
    }

    private Result<DisplayProfile> validate(DisplayProfile displayProfile){
        Result<DisplayProfile> result = new Result<DisplayProfile>();

        if (displayProfile == null) {
            result.addMessage("Profile cannot be null.", ResultType.INVALID);
            return result;
        }

        //validate that displayprofile has an appuserid attached

        if (displayProfile.getAppUserId() < 1) {
            result.addMessage("Profile must have an appUserId attached greater than or equal to 1.", ResultType.INVALID);
        }
        if (displayProfile.getBio() != null && displayProfile.getBio().length() > 500) {
            result.addMessage("Profile bio must not be longer than 500 characters.", ResultType.INVALID);
        }
        return result;
    }
}
