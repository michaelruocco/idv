package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LockoutPolicyItemsConverter {

    private final LockoutPolicyItemConverter itemConverter;

    public Collection<LockoutPolicy> queryOutcomesToPolicies(final ItemCollection<QueryOutcome> items) {
        return toPolicies(IterableUtils.toList(items));
    }

    public Collection<LockoutPolicy> scanOutcomesToPolicies(final ItemCollection<ScanOutcome> items) {
        return toPolicies(IterableUtils.toList(items));
    }

    public Collection<LockoutPolicy> toPolicies(final Collection<Item> items) {
        return items.stream()
                .map(this::toPolicy)
                .collect(Collectors.toList());
    }

    public LockoutPolicy toPolicy(final Item item) {
        return itemConverter.toPolicy(item);
    }

    public Item toItem(final LockoutPolicy policy) {
        return itemConverter.toItem(policy);
    }

}
