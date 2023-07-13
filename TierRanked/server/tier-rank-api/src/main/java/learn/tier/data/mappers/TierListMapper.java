package learn.tier.data.mappers;

import learn.tier.models.TierList;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TierListMapper implements RowMapper {
    @Override
    public TierList mapRow(ResultSet resultSet, int i) throws SQLException {
        TierList tierList = new TierList();
        tierList.setTierListId(resultSet.getInt("tier_list_id"));
        tierList.setName(resultSet.getString("name"));
        tierList.setDescription(resultSet.getString("description"));
        tierList.setTimestamp(resultSet.getObject("timestamp", LocalDateTime.class));
        tierList.setS_Tier(resultSet.getString("s_tier"));
        tierList.setA_Tier(resultSet.getString("a_tier"));
        tierList.setB_Tier(resultSet.getString("b_tier"));
        tierList.setC_Tier(resultSet.getString("c_tier"));
        tierList.setD_Tier(resultSet.getString("d_tier"));
        tierList.setE_Tier(resultSet.getString("e_tier"));
        tierList.setF_Tier(resultSet.getString("f_tier"));
        tierList.setUpvotes(resultSet.getInt("upvotes"));
        tierList.setDownvotes(resultSet.getInt("downvotes"));
        tierList.setAppUserId(resultSet.getInt("app_user_id"));
        tierList.setCategoryId(resultSet.getInt("category_id"));
        return tierList;
    }
}
