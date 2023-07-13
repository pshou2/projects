package learn.tier.domain;

import learn.tier.data.CommentRepository;
import learn.tier.data.TierListRepository;
import learn.tier.models.Comment;
import learn.tier.models.TierList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository repository;
    private final TierListRepository tierListRepository;

    public CommentService(CommentRepository repository, TierListRepository tierListRepository) {
        this.repository = repository;
        this.tierListRepository = tierListRepository;
    }

    public List<Comment> findAll() {
        return repository.findAll();
    }

    public List<Comment> findByTierList(int tierListId) {
        return repository.findByTierList(tierListId);
    }

    public Result<Comment> add(Comment comment) {
        Result<Comment> result = validate(comment);
        if (!result.isSuccess()) {
            return result;
        }

        if (comment.getCommentId() != 0) {
            result.addMessage("commentId cannot be set for 'add' operation", ResultType.INVALID);
            return result;
        }

        comment = repository.add(comment);
        result.setPayload(comment);
        return result;
    }

    public boolean deleteById(int commentId) {
        return repository.deleteById(commentId);
    }

    private Result<Comment> validate(Comment comment) {
        Result<Comment> result = new Result<>();
        if (comment == null) {
            result.addMessage("comment cannot be null", ResultType.INVALID);
            return result;
        }
        if (comment.getComment() == null || comment.getComment().isBlank()) {
            result.addMessage("comment body is required", ResultType.INVALID);
        }

        if (comment.getComment() != null && comment.getComment().length() > 1000) {
            result.addMessage("comment cannot be longer than 1000 characters.", ResultType.INVALID);
        }

        if (comment.getAppUserId() < 1) {
            result.addMessage("comment must have a valid associated appUserId.", ResultType.INVALID);
        }

        if (comment.getTierListId() < 1) {
            result.addMessage("comment must have a valid associated tierListId.", ResultType.INVALID);
        }

        return result;
    }

}
