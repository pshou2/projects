package learn.tier.data.mappers;

import learn.tier.models.DisplayProfile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DisplayProfileMapper implements RowMapper<DisplayProfile> {
    @Override
    public DisplayProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
       DisplayProfile displayProfile = new DisplayProfile();
       displayProfile.setDisplayProfileId(rs.getInt("display_profile_id"));
       displayProfile.setPicture(rs.getString("picture"));
       displayProfile.setBio(rs.getString("bio"));
       displayProfile.setTwitter(rs.getString("twitter"));
       displayProfile.setInstagram(rs.getString("instagram"));
       displayProfile.setTiktok(rs.getString("tiktok"));
       displayProfile.setUsername(rs.getString("username"));
       displayProfile.setAppUserId(rs.getInt("app_user_id"));
       return displayProfile;
    }
}
