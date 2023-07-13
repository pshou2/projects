package learn.tier.domain;

import learn.tier.data.DisplayProfileRepository;
import learn.tier.models.DisplayProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class DisplayProfileServiceTest {
    @Autowired
    DisplayProfileService service;

    @MockBean
    DisplayProfileRepository repository;

    //happy add
    @Test
    void shouldAdd(){
        DisplayProfile displayProfile = new DisplayProfile();
        displayProfile.setAppUserId(3);

        Result<DisplayProfile> result = service.add(displayProfile);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    //unhappy add
    @Test
    void shouldNotAddNull(){
        Result<DisplayProfile> result = service.add(null);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddWithoutAppUserId(){
        DisplayProfile displayProfile = new DisplayProfile();

        Result<DisplayProfile> result = service.add(displayProfile);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddIfDisplayProfileWithAppUserIdExists() {
        DisplayProfile displayProfile = new DisplayProfile();
        displayProfile.setAppUserId(2);

        when(repository.getUsageCount(displayProfile.getAppUserId())).thenReturn(1);
        Result<DisplayProfile> result = service.add(displayProfile);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddIfBioLengthGreaterThan500() {
        DisplayProfile displayProfile = new DisplayProfile();
        displayProfile.setAppUserId(2);
        displayProfile.setBio("*".repeat(1000));

        Result<DisplayProfile> result = service.add(displayProfile);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddWithDisplayProfileId(){
        DisplayProfile displayProfile = new DisplayProfile();
        displayProfile.setAppUserId(2);
        displayProfile.setDisplayProfileId(2);

        Result<DisplayProfile> result = service.add(displayProfile);
        assertEquals(ResultType.INVALID, result.getType());
    }

    //happy update
    @Test
    void shouldUpdate() {
        DisplayProfile displayProfile = new DisplayProfile();
        displayProfile.setDisplayProfileId(1);
        displayProfile.setBio("I am the muffin man");
        displayProfile.setAppUserId(1);

        when(repository.update(displayProfile)).thenReturn(true);
        Result<DisplayProfile> result = service.update(displayProfile);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    //unhappy update
    @Test
    void shouldNotUpdateZeroId() {
        DisplayProfile displayProfile = new DisplayProfile();
        displayProfile.setBio("I am the muffin man");
        displayProfile.setAppUserId(1);

        when(repository.update(displayProfile)).thenReturn(false);
        Result<DisplayProfile> result = service.update(displayProfile);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateNonExistingId(){
        DisplayProfile displayProfile = new DisplayProfile();
        displayProfile.setDisplayProfileId(999);
        displayProfile.setBio("I am the muffin man");
        displayProfile.setAppUserId(1);

        when(repository.update(displayProfile)).thenReturn(false);
        Result<DisplayProfile> result = service.update(displayProfile);
        assertEquals(ResultType.NOT_FOUND, result.getType());
    }

    //happy delete
    @Test
    void shouldDelete(){
        when(repository.deleteById(1)).thenReturn(true);
        Result<DisplayProfile> result = service.deleteById(1);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotDeleteNonExistingId(){
        when(repository.deleteById(999)).thenReturn(false);
        Result<DisplayProfile> result = service.deleteById(999);
        assertEquals(ResultType.NOT_FOUND, result.getType());
    }
}