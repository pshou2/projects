package learn.tier.data;

import learn.tier.models.Category;

import java.util.List;

public interface CategoryRepository {

    List<Category> findAll();


    Category findById(int categoryId);

    Category add(Category category);

    boolean update(Category category);

    boolean deleteById(int categoryId);
}
