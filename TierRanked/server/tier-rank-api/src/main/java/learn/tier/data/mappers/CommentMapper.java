package learn.tier.data.mappers;

import learn.tier.models.Comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CommentMapper implements RowMapper {
    @Override
    public Comment mapRow(ResultSet resultSet, int i) throws SQLException {
        Comment comment = new Comment();
        comment.setCommentId(resultSet.getInt("comment_id"));
        comment.setComment(resultSet.getString("comment"));
        comment.setTimestamp(resultSet.getObject("timestamp", LocalDateTime.class));
        comment.setTierListId(resultSet.getInt("tier_list_id"));
        comment.setAppUserId(resultSet.getInt("app_user_id"));
        return comment;
    }
}
