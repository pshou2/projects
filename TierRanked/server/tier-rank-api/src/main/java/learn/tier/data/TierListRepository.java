package learn.tier.data;

import learn.tier.models.TierList;

import java.util.List;

public interface TierListRepository {

    public List<TierList> findAll();
    public List<TierList> findByCategory(int categoryId);

    public TierList findById(int tier_list_id);

    public List<TierList> findByUser(int appUserId);
    public TierList add(TierList tierList);
    public boolean update(TierList tierList);
    public boolean deleteById(int tierListId);

}
