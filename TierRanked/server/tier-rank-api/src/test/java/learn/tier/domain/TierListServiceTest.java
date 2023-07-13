package learn.tier.domain;

import learn.tier.data.TierListRepository;
import learn.tier.models.Comment;
import learn.tier.models.TierList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@SpringBootTest
public class TierListServiceTest {
    @Autowired
    TierListService service;

    @MockBean
    TierListRepository repository;

    @Test
    void shouldFindById() {
        TierList tierList = makeTierList();
        when(repository.findById(1)).thenReturn(tierList);

        TierList actual = service.findById(1);

        assertNotNull(actual);
        assertEquals(tierList, actual);
    }

    @Test
    void shouldNotFindByIdInvalid() {
        TierList tierList = makeTierList();
        when(repository.findById(1000)).thenReturn(null);

        TierList actual = service.findById(1000);

        assertNull(actual);
    }
    @Test
    void shouldAddWhenValid() {
        TierList tierList = makeTierList();
        TierList mockOut = makeTierList();
        mockOut.setTierListId(0);

        when(repository.add(tierList)).thenReturn(mockOut);

        Result<TierList> actual = service.add(tierList);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }

    @Test
    void shouldNotAddNull() {
        // Should not add `null`.
        Result<TierList> result = service.add(null);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddIdGreaterThanZero() {
        // Should not add if tierListId is greater than 0.
        TierList tierList = makeTierList();
        tierList.setTierListId(1);
        Result<TierList> result = service.add(tierList);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddNullName() {
        // Should not add if name is null.
        TierList tierList = makeTierList();
        tierList.setTierListId(0);
        tierList.setName(null);
        Result<TierList> result = service.add(tierList);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddEmptyName() {
        // Should not add if name is empty.
        TierList tierList = makeTierList();
        tierList.setTierListId(0);
        tierList.setName("");
        Result<TierList> result = service.add(tierList);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddNameGreaterThan100() {
        TierList tierList = makeTierList();
        tierList.setName("a".repeat(101));

        Result<TierList> result = service.add(tierList);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddDescriptionGreaterThan500() {
        TierList tierList = makeTierList();
        tierList.setDescription("a".repeat(501));

        Result<TierList> result = service.add(tierList);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddWithoutAppUserId() {
        TierList tierList = new TierList();
        tierList.setName("Test Name");
        tierList.setTimestamp(LocalDateTime.now());
        tierList.setS_Tier("[\"images/test\"]");
        tierList.setUpvotes(1);
        tierList.setDownvotes(1);
        tierList.setCategoryId(1);
        Result<TierList> result = service.add(tierList);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddWithoutCategoryId() {
        TierList tierList = new TierList();
        tierList.setName("Test Name");
        tierList.setTimestamp(LocalDateTime.now());
        tierList.setS_Tier("[\"images/test\"]");
        tierList.setUpvotes(1);
        tierList.setDownvotes(1);
        tierList.setAppUserId(1);
        Result<TierList> result = service.add(tierList);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldUpdateWhenValid() {
        TierList tierList = makeTierList();
        tierList.setTierListId(1);
        tierList.setName("Updated Test");

        when(repository.update(tierList)).thenReturn(true);
        Result<TierList> actual = service.update(tierList);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdateNull() {
        // Should not update `null`.
        Result<TierList> result = service.update(null);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateNullName() {
        // Should not update if name is null.
        TierList tierList = makeTierList();
        tierList.setName(null);
        Result<TierList> result = service.update(tierList);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateEmptyName() {
        // Should not update if name is empty.
        TierList tierList = makeTierList();
        tierList.setName("");
        Result<TierList> result = service.update(tierList);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateIdEqualToZero() {
        // Should not update if tierListId is set to 0.
        TierList tierList = makeTierList();
        tierList.setTierListId(0);
        Result<TierList> result = service.update(tierList);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateNotFound() {
        // Should not update if not found.
        TierList tierList = makeTierList();
        tierList.setTierListId(999);
        // Mocking the repository update method to return false is not strictly necessary
        // as the default mock implementation for that method will return false.
        // Adding here for clarity on what our expectations are.
        when(repository.update(tierList)).thenReturn(false);
        Result<TierList>result = service.update(tierList);
        assertEquals(ResultType.NOT_FOUND, result.getType());
    }

    @Test
    void shouldNotUpdateNameGreaterThan100() {
        TierList tierList = makeTierList();
        tierList.setName("a".repeat(101));

        Result<TierList> result = service.update(tierList);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateDescriptionGreaterThan500() {
        TierList tierList = makeTierList();
        tierList.setDescription("a".repeat(501));

        Result<TierList> result = service.update(tierList);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateWithoutAppUserId() {
        TierList tierList = new TierList();
        tierList.setName("Test Name");
        tierList.setTimestamp(LocalDateTime.now());
        tierList.setS_Tier("[\"images/test\"]");
        tierList.setUpvotes(1);
        tierList.setDownvotes(1);
        tierList.setCategoryId(1);
        Result<TierList> result = service.update(tierList);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateWithoutCategoryId() {
        TierList tierList = new TierList();
        tierList.setName("Test Name");
        tierList.setTimestamp(LocalDateTime.now());
        tierList.setS_Tier("[\"images/test\"]");
        tierList.setUpvotes(1);
        tierList.setDownvotes(1);
        tierList.setAppUserId(1);
        Result<TierList> result = service.update(tierList);
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