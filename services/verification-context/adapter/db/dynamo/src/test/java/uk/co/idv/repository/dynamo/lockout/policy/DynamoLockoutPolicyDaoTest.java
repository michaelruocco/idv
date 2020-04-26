package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.LockoutPolicyRequest;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyDao;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DynamoLockoutPolicyDaoTest {

    private final Table table = mock(Table.class);
    private final Index index = mock(Index.class);
    private final LockoutPolicyItemsConverter converter = mock(LockoutPolicyItemsConverter.class);

    private LockoutPolicyDao dao;

    @BeforeEach
    void setUp() {
        given(table.getIndex("channelIdIndex")).willReturn(index);
        dao = DynamoLockoutPolicyDao.builder()
                .table(table)
                .converter(converter)
                .build();
    }

    @Test
    void shouldSaveLockoutPolicy() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        final Item item = mock(Item.class);
        given(converter.toItem(policy)).willReturn(item);

        dao.save(policy);

        verify(table).putItem(item);
    }

    @Test
    void shouldLoadAllLockoutPolicies() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        final ItemCollection<ScanOutcome> collection = mock(ItemCollection.class);
        given(table.scan()).willReturn(collection);
        final Collection<LockoutPolicy> expectedPolicies = Collections.singleton(policy);
        given(converter.scanOutcomesToPolicies(collection)).willReturn(expectedPolicies);

        final Collection<LockoutPolicy> policies = dao.load();

        assertThat(policies).isEqualTo(expectedPolicies);
    }

    @Test
    void shouldLoadExistingLockoutPolicyById() {
        final Item loadedItem = mock(Item.class);
        final LockoutPolicy expectedPolicy = LockoutPolicyMother.hardLockoutPolicy();
        given(table.getItem("id", expectedPolicy.getId().toString())).willReturn(loadedItem);
        given(converter.toPolicy(loadedItem)).willReturn(expectedPolicy);

        final Optional<LockoutPolicy> policy = dao.load(expectedPolicy.getId());

        assertThat(policy).contains(expectedPolicy);
    }

    @Test
    void shouldReturnEmptyOptionalIfLockoutPolicyDoesNotExist() {
        final UUID id = UUID.randomUUID();
        given(table.getItem("id", id.toString())).willReturn(null);

        final Optional<LockoutPolicy> policy = dao.load(id);

        assertThat(policy).isEmpty();
    }

    @Test
    void shouldLoadLockoutPoliciesByChannelId() {
        final String channelId = "channel-id";
        final LockoutPolicyRequest request = mock(LockoutPolicyRequest.class);
        given(request.getChannelId()).willReturn(channelId);
        final ItemCollection<QueryOutcome> items = mock(ItemCollection.class);
        given(index.query(new ChannelIdQuery(channelId))).willReturn(items);
        final LockoutPolicy expectedPolicy = LockoutPolicyMother.hardLockoutPolicy();
        given(converter.queryOutcomesToPolicies(items)).willReturn(Collections.singleton(expectedPolicy));

        final Collection<LockoutPolicy> policies = dao.load(request);

        assertThat(policies).containsExactly(expectedPolicy);
    }

}
