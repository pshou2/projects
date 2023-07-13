package learn.tier.data;

import learn.tier.models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryJdbcTemplateRepositoryTest {

    @Autowired
    CategoryJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup(){
        knownGoodState.set();
    }

    @Test
    void findAll() {
        List<Category> actual = repository.findAll();

        assertNotNull(actual);
    }

    @Test
    void findById(){
        Category result = repository.findById(2);
        assertNotNull(result);
    }

    @Test
    void shouldAddCategory() {
        Category category = new Category();
        category.setName("Animals");

        Category result = repository.add(category);

        assertNotNull(result);
        assertEquals(6, result.getCategoryId());
    }

    @Test
    void update() {
        Category category = new Category();
        category.setCategoryId(3);
        category.setName("Animals");

        assertTrue(repository.update(category));
        //assertEquals(category, repository.findById(3));
    }

    @Test
    void deleteById() {
        assertTrue(repository.deleteById(3));
    }

    @Test
    void shouldNotDeleteById(){
        assertFalse(repository.deleteById(1000));
    }

    @Test
    void shouldNotUpdateIdInvalid() {
        Category category = new Category();
        category.setCategoryId(1000);
        category.setName("Animals");

        assertFalse(repository.update(category));
        //assertEquals(category, repository.findById(3));
    }
}