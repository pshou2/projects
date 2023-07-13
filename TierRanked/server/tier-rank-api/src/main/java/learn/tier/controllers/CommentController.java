package learn.tier.controllers;

import learn.tier.domain.CommentService;
import learn.tier.domain.Result;
import learn.tier.models.Comment;
import learn.tier.models.TierList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://tierlist-front.s3-website.us-east-2.amazonaws.com"})
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Comment> findAll() {
        return service.findAll();
    }

    @GetMapping("/tierlist/{tierListId}")
    public List<Comment> findByTierList(@PathVariable int tierListId) {
        return service.findByTierList(tierListId);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Comment comment) {
        Result<Comment> result = service.add(comment);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteById(@PathVariable int commentId) {
        if (service.deleteById(commentId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}