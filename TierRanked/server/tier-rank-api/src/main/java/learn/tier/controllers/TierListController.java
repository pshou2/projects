package learn.tier.controllers;

import learn.tier.domain.Result;
import learn.tier.domain.TierListService;
import learn.tier.models.TierList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://tierlist-front.s3-website.us-east-2.amazonaws.com"})
@RequestMapping("/api/tierlist")
public class TierListController {

    private final TierListService service;

    public TierListController(TierListService service) {
        this.service = service;
    }

    @GetMapping
    public List<TierList> findAll() {
        return service.findAll();
    }

    @GetMapping("/{tierListId}")
    public TierList findById(@PathVariable int tierListId) {
        return service.findById(tierListId);
    }

    @GetMapping("/user/{appUserId}")
    public List<TierList> findByUser(@PathVariable int appUserId) {
        return service.findByUser(appUserId);
    }

    @GetMapping("/category/{categoryId}")
    public List<TierList> findByCategory(@PathVariable int categoryId) {
        return service.findByCategory(categoryId);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody TierList tierList) {
        Result<TierList> result = service.add(tierList);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{tierListId}")
    public ResponseEntity<Object> update(@PathVariable int tierListId, @RequestBody TierList tierList) {
        if (tierListId != tierList.getTierListId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<TierList> result = service.update(tierList);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{tierListId}")
    public ResponseEntity<Void> deleteById(@PathVariable int tierListId) {
        if (service.deleteById(tierListId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}