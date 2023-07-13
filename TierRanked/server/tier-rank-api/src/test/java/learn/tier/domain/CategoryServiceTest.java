package learn.tier.domain;

import learn.tier.data.CategoryRepository;
import learn.tier.models.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    CategoryService service;

    @MockBean
    CategoryRepository repository;

    @Test
    void shouldFindAll() {
        when(repository.findAll()).thenReturn(List.of(
                new Category(1, "Baseball"),
                new Category(2, "Football"),
                new Category(3, "Basketball")
        ));

        List<Category> categories = service.findAll();

        assertEquals(3, categories.size());
    }

    @Test
    void findById() {
        when(repository.findById(3)).thenReturn(new Category());
        Category category = service.findById(3);
        assertNotNull(category);
    }

    @Test
    void shouldAdd() {
        Category category = new Category();
        category.setName("Animals");

        when(repository.add(category)).thenReturn(category);

        Result<Category> result = service.add(category);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
    }

    @Test
    void shouldUpdate() {
        Category category = new Category(1, "Baseball");

        when(repository.update(category)).thenReturn(true);

        Result<Category> result = service.update(category);

        assertTrue(result.isSuccess());

    }

    @Test
    void shouldDeleteById() {
        when(repository.deleteById(3)).thenReturn(true);

        boolean result = service.deleteById(3);

        assertTrue(result);
    }

    // UNHAPPY PATH //

    @Test
    void shouldNotDeleteById() {
        when(repository.deleteById(1000)).thenReturn(false);

        boolean result = service.deleteById(1000);

        assertFalse(result);
    }

    @Test
    void shouldNotUpdateIdInvalid() {
        Category category = new Category();
        category.setCategoryId(1000);
        category.setName("Animals");

        when(repository.update(category)).thenReturn(false);

        Result<Category> result = service.update(category);

        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotFindById() {
        when(repository.findById(1000)).thenReturn(null);

        Category result = service.findById(1000);

        assertNull(result);
    }

    @Test
    void shouldNotFindAll() {
        when(repository.findAll()).thenReturn(null);

        List<Category> result = service.findAll();

        assertNull(result);
    }

    @Test
    void shouldNotAddDuplicateCategory() {
        // Create a category with the same name as an existing category
        Category category = new Category();
        category.setName("Animals");

        // Mock the behavior of the repository to return a list of categories
        List<Category> existingCategories = new ArrayList<>();
        existingCategories.add(category);
        when(repository.findAll()).thenReturn(existingCategories);

        // Call the service method
        Result<Category> result = service.add(category);

        // Assert that the result is not successful
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }
}