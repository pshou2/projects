package learn.tier.domain;

import learn.tier.data.CommentRepository;
import learn.tier.models.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    CommentService service;

    @MockBean
    CommentRepository repository;

    @Test
    void shouldAddWhenValid() {
        Comment comment = makeComment();
        Comment mockOut = makeComment();
        mockOut.setCommentId(0);

        when(repository.add(comment)).thenReturn(mockOut);

        Result<Comment> actual = service.add(comment);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }

    @Test
    void shouldNotAddNull() {
        // Should not add `null`.
        Result<Comment> result = service.add(null);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddIdGreaterThanZero() {
        // Should not add if commentId is greater than 0.
        Comment comment = makeComment();
        comment.setCommentId(1);
        Result<Comment> result = service.add(comment);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddNullComment() {
        // Should not add if comment is null.
        Comment comment = makeComment();
        comment.setCommentId(0);
        comment.setComment(null);
        Result<Comment> result = service.add(comment);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddEmptyComment() {
        // Should not add if comment is empty.
        Comment comment = makeComment();
        comment.setCommentId(0);
        comment.setComment("");
        Result<Comment> result = service.add(comment);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddCommentGreaterThan1000() {
        Comment comment = makeComment();
        comment.setComment("a".repeat(1001));
        Result<Comment> result = service.add(comment);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddWithoutAppUserId() {
        Comment comment = new Comment();
        comment.setComment("test");
        comment.setTimestamp(LocalDateTime.now());
        comment.setTierListId(1);
        Result<Comment> result = service.add(comment);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddWithoutTierListId() {
        Comment comment = new Comment();
        comment.setComment("test");
        comment.setTimestamp(LocalDateTime.now());
        comment.setAppUserId(2);
        Result<Comment> result = service.add(comment);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldDelete() {
        when(repository.deleteById(1)).thenReturn(true);
        assertTrue(service.deleteById(1));
    }

    @Test
    void shouldNotDeleteWhenNotFound() {
        when(repository.deleteById(999)).thenReturn(false);
        assertFalse(service.deleteById(999));
    }

    Comment makeComment() {
        Comment comment = new Comment();
        comment.setComment("test");
        comment.setTimestamp(LocalDateTime.now());
        comment.setTierListId(1);
        comment.setAppUserId(2);
        return comment;
    }

}