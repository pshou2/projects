package learn.tier.domain;

import learn.tier.data.TierListRepository;
import learn.tier.models.TierList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TierListService {

    private final TierListRepository repository;

    public TierListService(TierListRepository repository) {
        this.repository = repository;
    }

    public List<TierList> findAll() {
        return repository.findAll();
    }

    public TierList findById(int tierListId) {
        return repository.findById(tierListId);
    }

    public List<TierList> findByUser(int appUserId) {
        return repository.findByUser(appUserId);
    }

    public List<TierList> findByCategory(int categoryId) {
        return repository.findByCategory(categoryId);
    }

    public Result<TierList> add(TierList tierList) {
        Result<TierList> result = validate(tierList);
        if (!result.isSuccess()) {
            return result;
        }

        if (tierList.getTierListId() != 0) {
            result.addMessage("tierListId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        tierList = repository.add(tierList);
        result.setPayload(tierList);
        return result;
    }

    public Result<TierList> update(TierList tierList) {
        Result<TierList> result = validate(tierList);
        if (!result.isSuccess()) {
            return result;
        }

        if (tierList.getTierListId() <= 0) {
            result.addMessage("tierListId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(tierList)) {
            String msg = String.format("tierListId: %s, not found", tierList.getTierListId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int tierListId) {
        return repository.deleteById(tierListId);
    }

    private Result<TierList> validate(TierList tierList) {
        Result<TierList> result = new Result<>();

        if (tierList == null) {
            result.addMessage("tierList cannot be null.", ResultType.INVALID);
            return result;
        }

        if (tierList.getName() == null || tierList.getName().isBlank()) {
            result.addMessage("TierList `name` is required.", ResultType.INVALID);
        }

        if (tierList.getName() != null && tierList.getName().length() > 100) {
            result.addMessage("name cannot be longer than 100 characters.", ResultType.INVALID);
        }

        if (tierList.getDescription() != null && tierList.getDescription().length() > 500) {
            result.addMessage("description cannot be longer than 500 characters.", ResultType.INVALID);
        }

        if (tierList.getAppUserId() < 1) {
            result.addMessage("tierList must have a valid associated appUserId.", ResultType.INVALID);
        }

        if (tierList.getCategoryId() < 1) {
            result.addMessage("tierList must have a valid associated categoryId.", ResultType.INVALID);
        }

        return result;
    }

}
