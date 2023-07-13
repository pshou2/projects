package learn.tier.data;

import learn.tier.data.mappers.DisplayProfileMapper;
import learn.tier.models.AppUser;
import learn.tier.models.DisplayProfile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class DisplayProfileJdbcTemplateRepository implements DisplayProfileRepository {
    //constructor
    private final JdbcTemplate jdbcTemplate;

    public DisplayProfileJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<DisplayProfile> findAll() {
        final String sql = "select display_profile_id, picture, bio, twitter, instagram, tiktok, username, app_user_id "
                + "from display_profile;";

        return jdbcTemplate.query(sql, new DisplayProfileMapper());
    }

    @Override
    public DisplayProfile findByUserId(int appUserId) {
        final String sql = "select display_profile_id, picture, bio, twitter, instagram, tiktok, username, app_user_id "
                + "from display_profile "
                + "where app_user_id = ?;";

        return jdbcTemplate.queryForObject(sql, new DisplayProfileMapper(), appUserId); //find app user id
    }

    @Override
    public DisplayProfile findByUsername(String username) {
        final String sql = "select display_profile_id, picture, bio, twitter, instagram, tiktok, username, app_user_id "
                + "from display_profile "
                + "where username = ?;";

        return jdbcTemplate.queryForObject(sql, new DisplayProfileMapper(), username);
    }

    @Override
    public DisplayProfile add(DisplayProfile displayProfile){
        if (getUsageCount(displayProfile.getAppUserId()) >= 1) {
            return null;
        } else {
            final String sql = "insert into display_profile (picture, bio, twitter, instagram, tiktok, username, app_user_id) "
                    + "values (?,?,?,?,?,?,?);";

            KeyHolder keyHolder = new GeneratedKeyHolder();
            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,displayProfile.getPicture());
                ps.setString(2,displayProfile.getBio());
                ps.setString(3,displayProfile.getTwitter());
                ps.setString(4,displayProfile.getInstagram());
                ps.setString(5,displayProfile.getTiktok());
                ps.setString(6, displayProfile.getUsername());
                ps.setInt(7,displayProfile.getAppUserId());
                return ps;
            }, keyHolder);

            if(rowsAffected <= 0){
                return null;
            }

            displayProfile.setDisplayProfileId(keyHolder.getKey().intValue());

            return displayProfile;
        }
    }

    @Override
    public boolean update(DisplayProfile displayProfile){
        final String sql = "update display_profile set "
                + "picture = ?, "
                + "bio = ?, "
                + "twitter = ?, "
                + "instagram = ?, "
                + "tiktok = ? "
                + "where app_user_id = ?;";

        return jdbcTemplate.update(sql,
                displayProfile.getPicture(),
                displayProfile.getBio(),
                displayProfile.getTwitter(),
                displayProfile.getInstagram(),
                displayProfile.getTiktok(),
                displayProfile.getAppUserId()) > 0;
    }

    @Override
    public boolean deleteById(int appUserId) {
        return jdbcTemplate.update("delete from display_profile where app_user_id = ?;", appUserId) > 0;
    }

    @Override
    public int getUsageCount(int appUserId) {
        String sql = "select * from display_profile where app_user_id = ?;";
        return (int) jdbcTemplate.query("select * from display_profile where app_user_id = ?;", new DisplayProfileMapper(), appUserId).stream().count();
    }
}
