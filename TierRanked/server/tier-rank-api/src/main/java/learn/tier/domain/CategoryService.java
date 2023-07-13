package learn.tier.domain;

import learn.tier.data.CategoryRepository;
import learn.tier.models.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAll(){
        return repository.findAll();
    }

    public Category findById(int categoryId){
        return repository.findById(categoryId);
    }

    public Result<Category> add (Category category){
        Result<Category> result = validate(category);
        if(!result.isSuccess()){
            return result;
        }

        if(category.getCategoryId() != 0){
            result.addMessage("Category Id cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        category = repository.add(category);
        result.setPayload(category);
        return result;
    }

    public Result<Category> update(Category category){
        Result<Category> result = validate(category);
        if (!result.isSuccess()) {
            return result;
        }

        if (category.getCategoryId() <= 0) {
            result.addMessage("Category Id must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(category)) {
            String msg = String.format("Category Id: %s, not found", category.getCategoryId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int categoryId) {
        return repository.deleteById(categoryId);
    }

    private Result<Category> validate(Category category) {
        Result<Category> result = new Result<>();

        if(category == null){
            result.addMessage("Category cannot be null", ResultType.INVALID);
            return result;
        }

        List<Category> existingCategories = repository.findAll();

        for(Category existingCategory : existingCategories){
            if(existingCategory.getName().equalsIgnoreCase(category.getName())){
                result.addMessage("Category already exist", ResultType.INVALID);
                return result;
            }
        }

        return result;
    }


}
