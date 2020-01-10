package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.document.Index;
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

@Slf4j
public class DynamoLockoutPolicyDao implements LockoutPolicyDao {

    private final Table table;
    private final Index channelIdIndex;

    private final LockoutPolicyItemConverter converter;
    private final MultipleLockoutPoliciesHandler multiplePoliciesHandler;

    @Builder
    private DynamoLockoutPolicyDao(final Table table,
                                   final LockoutPolicyItemConverter converter,
                                   final MultipleLockoutPoliciesHandler multiplePoliciesHandler) {
        this.table = table;
        this.channelIdIndex = table.getIndex("channelIdIndex");
        this.converter = converter;
        this.multiplePoliciesHandler = multiplePoliciesHandler;
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
    public Optional<LockoutPolicy> load(final LockoutPolicyRequest request) {
        final QuerySpec query = toQuery(request);
        final ItemCollection<QueryOutcome> items = channelIdIndex.query(query);
        final List<LockoutPolicy> applicablePolicies = IterableUtils.toList(items).stream()
                .map(converter::toPolicy)
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
