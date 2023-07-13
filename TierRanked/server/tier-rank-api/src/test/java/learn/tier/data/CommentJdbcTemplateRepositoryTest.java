package learn.tier.data;

import learn.tier.models.Comment;
import learn.tier.models.TierList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentJdbcTemplateRepositoryTest {

    final static int NEXT_ID = 3;

    @Autowired
    CommentJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Comment> comments = repository.findAll();
        assertNotNull(comments);
        assertTrue(comments.size() >= 1 && comments.size() <= NEXT_ID);
    }

    @Test
    void shouldFindByTierList() {
        List<Comment> comments = repository.findByTierList(2);
        assertNotNull(comments);
        assertEquals(2, comments.size());
    }

    @Test
    void shouldNotFindCommentsInUnknownList() {
        List<Comment> comments = repository.findByTierList(999);
        assertNotNull(comments);
        assertEquals(0, comments.size());
    }

    @Test
    void shouldAdd() {
        Comment comment = makeComment();
        comment.setCommentId(NEXT_ID);
        Comment actual = repository.add(comment);
        assertNotNull(actual);
        assertEquals(3, actual.getCommentId());
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(2));
        assertFalse(repository.deleteById(2));
    }

    @Test
    void shouldNotDeleteUnknown() {
        assertFalse(repository.deleteById(9999));
    }

    Comment makeComment() {
        Comment comment = new Comment();
        comment.setComment("test");
        comment.setTimestamp(LocalDateTime.now());
        comment.setTierListId(2);
        comment.setAppUserId(2);
        return comment;
    }

    TierList makeTierList() {
        TierList tierList = new TierList();
        tierList.setTierListId(3);
        tierList.setName("Test Name");
        tierList.setDescription("Test Description");
        tierList.setTimestamp(LocalDateTime.now());
        tierList.setS_Tier("[\"images/test\"]");
        tierList.setA_Tier("[\"images/test\"]");
        tierList.setB_Tier("[\"images/test\"]");
        tierList.setC_Tier("[\"images/test\"]");
        tierList.setD_Tier("[\"images/test\"]");
        tierList.setE_Tier("[\"images/test\"]");
        tierList.setF_Tier("[\"images/test\"]");
        tierList.setUpvotes(1);
        tierList.setDownvotes(1);
        tierList.setAppUserId(1);
        tierList.setCategoryId(1);
        return tierList;
    }

}