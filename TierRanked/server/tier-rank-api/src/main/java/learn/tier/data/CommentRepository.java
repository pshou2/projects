package learn.tier.data;

import learn.tier.models.Comment;
import learn.tier.models.TierList;

import java.util.List;

public interface CommentRepository {

    public List<Comment> findAll();
    public List<Comment> findByTierList(int tierListId);
    public Comment add(Comment comment);
    public boolean deleteById(int commentId);

}
