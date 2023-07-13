package learn.tier.controllers;

import learn.tier.domain.DisplayProfileService;
import learn.tier.domain.Result;
import learn.tier.models.AppUser;
import learn.tier.models.DisplayProfile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://tierlist-front.s3-website.us-east-2.amazonaws.com"})
@RequestMapping("/api/display_profile")
public class DisplayProfileController {
    private final DisplayProfileService service;

    public DisplayProfileController(DisplayProfileService service) {
        this.service = service;
    }

    @GetMapping
    public List<DisplayProfile> findAll(){
        return service.findAll();
    }

    @GetMapping("/{appUserId}")
    public ResponseEntity<DisplayProfile> findByUserId(@PathVariable int appUserId) {
        DisplayProfile displayProfile = service.findByUserId(appUserId);
        if(displayProfile == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(displayProfile, HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<DisplayProfile> findByUsername(@PathVariable String username) {
        DisplayProfile displayProfile = service.findByUsername(username);
        if(displayProfile == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(displayProfile, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody DisplayProfile displayProfile) {
        Result<DisplayProfile> result = service.add(displayProfile);
        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }

        return ErrorResponse.build(result);
    }

    @PutMapping("/{appUserId}")
    public ResponseEntity<Object> update(@PathVariable int appUserId, @RequestBody DisplayProfile displayProfile){
        if (appUserId != displayProfile.getAppUserId()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<DisplayProfile> result = service.update(displayProfile);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{appUserId}")
    ResponseEntity<Object> deleteById(@PathVariable int appUserId) {
        Result<DisplayProfile> result = service.deleteById(appUserId);
        if (result.isSuccess()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

}
