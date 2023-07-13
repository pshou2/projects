package learn.tier.data;

import learn.tier.models.AppUser;
import learn.tier.models.DisplayProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DisplayProfileJdbcTemplateRepositoryTest {

    @Autowired
    DisplayProfileJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup(){
        knownGoodState.set();
    }

    @Test
    void shouldFindAll(){
        List<DisplayProfile> all = repository.findAll();
        for (DisplayProfile d : all) {
            System.out.println(d.getBio());
        }
    }

    @Test
    void shouldFindByUsername(){
        DisplayProfile actual = repository.findByUsername("john@smith.com");
        assertNotNull(actual);
        assertEquals("john@smith.com",actual.getUsername());
        assertEquals("cereal enthusiast", actual.getBio());
    }

    @Test
    void shouldFindByUserId(){
//        AppUser appUser = new AppUser(1, "john@smith.com", "password", true, new ArrayList<String>());
        DisplayProfile actual = repository.findByUserId(1);

        System.out.println(actual.getDisplayProfileId());
        System.out.println(actual.getBio());
        assertNotNull(actual);
    }

    @Test
    void shouldAddDisplayProfile(){
        DisplayProfile profileToAdd = new DisplayProfile();
        profileToAdd.setBio("hello world");
        profileToAdd.setAppUserId(3);
        profileToAdd.setUsername("phil@shou.com");
        DisplayProfile actual = repository.add(profileToAdd);
        assertNotNull(actual);
    }

    @Test
    void shouldNotAddDisplayProfile(){
        DisplayProfile profileToAdd = new DisplayProfile();
        profileToAdd.setBio("hello world");
        profileToAdd.setAppUserId(1);

        DisplayProfile actual = repository.add(profileToAdd);
        assertNull(actual);
    }

    @Test
    void shouldUpdateProfile(){
        DisplayProfile profileToUpdate = new DisplayProfile();
        profileToUpdate.setPicture("new pfp url");
        profileToUpdate.setBio("this is a new bio");
        profileToUpdate.setAppUserId(1);
        profileToUpdate.setDisplayProfileId(1);

        assertTrue(repository.update(profileToUpdate));
    }

    @Test
    void shouldDeleteProfile(){
        assertTrue(repository.deleteById(2));
    }
}