package learn.tier.data;

import learn.tier.data.mappers.TierListMapper;
import learn.tier.models.TierList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TierListJdbcTemplateRepository implements TierListRepository {

    private final JdbcTemplate jdbcTemplate;


    public TierListJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TierList> findAll() {
        final String sql = "select tier_list_id, `name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, e_tier, f_tier, upvotes, downvotes, app_user_id, category_id "
                + "from tier_list limit 1000;";
        return jdbcTemplate.query(sql, new TierListMapper());
    }

    @Override
    public List<TierList> findByCategory(int categoryId) {
        final String sql = "select tier_list_id, `name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, e_tier, f_tier, upvotes, downvotes, app_user_id, category_id "
                + "from tier_list "
                + "where category_id = ?;";
        return jdbcTemplate.query(sql, new TierListMapper(), categoryId);
    }

    @Override
    public TierList findById(int tier_list_id) {
        final String sql = "select tier_list_id, `name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, e_tier, f_tier, upvotes, downvotes, app_user_id, category_id "
                + "from tier_list "
                + "where tier_list_id = ?;";
        return (TierList) jdbcTemplate.query(sql, new TierListMapper(), tier_list_id).stream().findFirst().orElse(null);
    }

    @Override
    public List<TierList> findByUser(int appUserId) {
        final String sql = "select tier_list_id, `name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, e_tier, f_tier, upvotes, downvotes, app_user_id, category_id "
                + "from tier_list "
                + "where app_user_id = ?;";
        return jdbcTemplate.query(sql, new TierListMapper(), appUserId);
    }

    @Override
    public TierList add(TierList tierList) {
        final String sql = "insert into tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, e_tier, f_tier, upvotes, downvotes, app_user_id, category_id) "
                + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tierList.getName());
            ps.setString(2, tierList.getDescription());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(tierList.getTimestamp()));
            ps.setString(4, tierList.getS_Tier());
            ps.setString(5, tierList.getA_Tier());
            ps.setString(6, tierList.getB_Tier());
            ps.setString(7, tierList.getC_Tier());
            ps.setString(8, tierList.getD_Tier());
            ps.setString(9, tierList.getE_Tier());
            ps.setString(10, tierList.getF_Tier());
            ps.setInt(11, tierList.getUpvotes());
            ps.setInt(12, tierList.getDownvotes());
            ps.setInt(13, tierList.getAppUserId());
            ps.setInt(14, tierList.getCategoryId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        tierList.setTierListId(keyHolder.getKey().intValue());
        return tierList;
    }

    @Override
    public boolean update(TierList tierList) {
        final String sql = "update tier_list set "
                + "`name` = ?, "
                + "`description` = ?, "
                + "`timestamp` = ?, "
                + "s_tier = ?, "
                + "a_tier = ?, "
                + "b_tier = ?, "
                + "c_tier = ?, "
                + "d_tier = ?, "
                + "e_tier = ?, "
                + "f_tier = ?, "
                + "upvotes = ?, "
                + "downvotes = ?, "
                + "app_user_id = ?, "
                + "category_id = ? "
                + "where tier_list_id = ?;";

        return jdbcTemplate.update(sql,
                tierList.getName(),
                tierList.getDescription(),
                tierList.getTimestamp(),
                tierList.getS_Tier(),
                tierList.getA_Tier(),
                tierList.getB_Tier(),
                tierList.getC_Tier(),
                tierList.getD_Tier(),
                tierList.getE_Tier(),
                tierList.getF_Tier(),
                tierList.getUpvotes(),
                tierList.getDownvotes(),
                tierList.getAppUserId(),
                tierList.getCategoryId(),
                tierList.getTierListId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int tierListId) {
        jdbcTemplate.update("delete from `comment` where tier_list_id = ?;", tierListId);
        return jdbcTemplate.update("delete from tier_list where tier_list_id = ?;", tierListId) > 0;
    }

}
