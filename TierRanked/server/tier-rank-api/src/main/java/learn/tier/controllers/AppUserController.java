package learn.tier.controllers;

import learn.tier.models.AppUser;
import learn.tier.security.AppUserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://tierlist-front.s3-website.us-east-2.amazonaws.com"})
@RequestMapping("/api/user")
public class AppUserController {

    private final AppUserService service;

    public AppUserController(AppUserService appUserService) {
        this.service = appUserService;
    }

    @GetMapping
    public List<AppUser> findAll() {
        return service.findAll();
    }

}
