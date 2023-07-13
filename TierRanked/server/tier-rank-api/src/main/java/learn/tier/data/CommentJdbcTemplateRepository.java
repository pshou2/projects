package learn.tier.data;

import learn.tier.data.mappers.CommentMapper;
import learn.tier.models.Comment;
import learn.tier.models.TierList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CommentJdbcTemplateRepository implements CommentRepository {

    private final JdbcTemplate jdbcTemplate;

    public CommentJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Comment> findAll() {
        final String sql = "select comment_id, `comment`, `timestamp`, tier_list_id, app_user_id "
                + "from `comment` limit 1000;";
        return jdbcTemplate.query(sql, new CommentMapper());
    }

    @Override
    public List<Comment> findByTierList(int tierListId) {
        final String sql = "select comment_id, `comment`, `timestamp`, tier_list_id, app_user_id "
                + "from `comment` "
                + "where tier_list_id = ?;";
        return jdbcTemplate.query(sql, new CommentMapper(), tierListId);
    }

    @Override
    public Comment add(Comment comment) {
        final String sql = "insert into `comment` (`comment`, `timestamp`, tier_list_id, app_user_id) "
                + " values (?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, comment.getComment());
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(comment.getTimestamp()));
            ps.setInt(3, comment.getTierListId());
            ps.setInt(4, comment.getAppUserId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        comment.setTierListId(keyHolder.getKey().intValue());
        return comment;
    }

    @Override
    public boolean deleteById(int commentId) {
        return jdbcTemplate.update("delete from `comment` where comment_id = ?;", commentId) > 0;
    }

}
