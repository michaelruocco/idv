package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import uk.co.idv.domain.entities.lockout.LockoutPolicyRequest;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyDao;
import uk.co.idv.domain.usecases.lockout.MultipleLockoutPoliciesHandler;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@Slf4j
public class DynamoLockoutPolicyDao implements LockoutPolicyDao {

    private final LockoutPolicyItemConverterDelegator converter;
    private final Table table;
    private final Index channelIdIndex;
    private final MultipleLockoutPoliciesHandler multiplePoliciesHandler;

    @Override
    public void save(final LockoutPolicy policy) {
        log.info("saving lockout policy {}", policy);
        final Item item = converter.toItem(policy);
        log.info("putting item {}", item);
        table.putItem(item);
    }

    @Override
    public Optional<LockoutPolicy> load(final UUID id) {
        log.info("loading lockout policy by id {}", id);
        final Item item = table.getItem("id", id.toString());
        if (item == null) {
            log.debug("lockout policy not found returning empty optional");
            return Optional.empty();
        }
        log.info("loaded item {}", item);
        final LockoutPolicy policy = converter.toPolicy(item);
        log.info("returning lockout policy {}", policy);
        return Optional.of(policy);
    }

    @Override
    public Optional<LockoutPolicy> load(final LockoutPolicyRequest request) {
        final QuerySpec query = toQuery(request);
        final ItemCollection<QueryOutcome> items = channelIdIndex.query(query);
        final List<LockoutPolicy> applicablePolicies = IterableUtils.toList(items).stream()
                .map(item -> item.getString("id"))
                .map(UUID::fromString)
                .map(this::load)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(policy -> policy.appliesTo(request))
                .collect(Collectors.toList());
        log.info("found applicable policies {} for request {}", applicablePolicies, request);
        return multiplePoliciesHandler.extractPolicy(applicablePolicies);
    }

    @Override
    public Collection<LockoutPolicy> load() {
        final ItemCollection<ScanOutcome> items = table.scan();
        return converter.toPolicies(items);
    }

    private QuerySpec toQuery(final LockoutPolicyRequest request) {
        final ValueMap valueMap = new ValueMap().withString(":channelId", request.getChannelId());
        return new QuerySpec()
                .withKeyConditionExpression("channelId = :channelId")
                .withValueMap(valueMap);
    }

}
