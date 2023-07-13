package learn.tier.controllers;

import learn.tier.domain.CategoryService;
import learn.tier.domain.Result;
import learn.tier.models.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = {"http://localhost:3000","http://tierlist-front.s3-website.us-east-2.amazonaws.com"})
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> findById(@PathVariable int categoryId) {
        Category category = categoryService.findById(categoryId);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Category category) {
        Result<Category> result = categoryService.add(category);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Object> update(@PathVariable int categoryId, @RequestBody Category category) {
        if (categoryId != category.getCategoryId()){
            return new ResponseEntity<>("Category Id must be set for `update` operation", HttpStatus.CONFLICT);
        }
        Result<Category> result = categoryService.update(category);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteById(@PathVariable int categoryId) {
        if (categoryService.deleteById(categoryId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
