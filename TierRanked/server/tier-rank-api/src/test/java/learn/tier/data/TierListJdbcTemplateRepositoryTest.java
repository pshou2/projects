package learn.tier.data;

import learn.tier.models.TierList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TierListJdbcTemplateRepositoryTest {

    final static int NEXT_ID = 3;

    @Autowired
    TierListJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<TierList> tierLists = repository.findAll();
        assertNotNull(tierLists);
        assertTrue(tierLists.size() >= 1 && tierLists.size() <= NEXT_ID);
    }

    @Test
    void shouldFindByCategory() {
        List<TierList> tierLists = repository.findByCategory(1);
        assertNotNull(tierLists);
        assertEquals(2, tierLists.size());
    }

    @Test
    void shouldFindById() {
        Object tierList = repository.findById(1);
        assertNotNull(tierList);
    }

    @Test
    void shouldNotFindByIdInvalid() {
        Object tierList = repository.findById(1000);
        assertNull(tierList);
    }

    @Test
    void shouldNotFindListsInUnknownCategory() {
        List<TierList> tierLists = repository.findByCategory(999);
        assertNotNull(tierLists);
        assertEquals(0, tierLists.size());
    }

    @Test
    void shouldFindByUser() {
        List<TierList> tierLists = repository.findByUser(2);
        assertNotNull(tierLists);
        assertEquals(1, tierLists.size());
    }

    @Test
    void shouldNotFindListsByUnknownUser() {
        List<TierList> tierLists = repository.findByCategory(999);
        assertNotNull(tierLists);
        assertEquals(0, tierLists.size());
    }

    @Test
    void shouldAdd() {
        TierList tierList = makeTierList();
        TierList actual = repository.add(tierList);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getTierListId());
    }

    @Test
    void shouldUpdate() {
        TierList tierList = makeTierList();
        tierList.setTierListId(2);
        tierList.setName("Updated Name");
        assertTrue(repository.update(tierList));
    }

    @Test
    void shouldNotUpdateUnknown() {
        TierList tierList = new TierList();
        tierList.setTierListId(NEXT_ID + 1);
        assertFalse(repository.update(tierList));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(1));
        assertFalse(repository.deleteById(1));
    }

    @Test
    void shouldNotDeleteUnknown() {
        assertFalse(repository.deleteById(999));
    }

    TierList makeTierList() {
        TierList tierList = new TierList();
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