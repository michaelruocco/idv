package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.lockout.LockoutPolicyRequest;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyDao;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class DynamoLockoutPolicyDao implements LockoutPolicyDao {

    private final Table table;
    private final Index channelIdIndex;
    private final LockoutPolicyItemsConverter converter;

    @Builder
    private DynamoLockoutPolicyDao(final Table table, final LockoutPolicyItemsConverter converter) {
        this.table = table;
        this.channelIdIndex = table.getIndex("channelIdIndex");
        this.converter = converter;
    }

    @Override
    public void save(final LockoutPolicy policy) {
        log.info("saving lockout policy {}", policy);
        table.putItem(converter.toItem(policy));
    }

    @Override
    public Optional<LockoutPolicy> load(final UUID id) {
        log.info("loading lockout policy by id {}", id);
        return Optional.ofNullable(table.getItem("id", id.toString()))
                .map(converter::toPolicy);
    }

    @Override
    public Collection<LockoutPolicy> load(final LockoutPolicyRequest request) {
        final QuerySpec query = new ChannelIdQuery(request.getChannelId());
        final ItemCollection<QueryOutcome> items = channelIdIndex.query(query);
        return converter.queryOutcomesToPolicies(items);
    }

    @Override
    public Collection<LockoutPolicy> load() {
        final ItemCollection<ScanOutcome> items = table.scan();
        return converter.scanOutcomesToPolicies(items);
    }

}
