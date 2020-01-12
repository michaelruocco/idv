package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.document.Item;
import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LockoutPolicyItemsConverter {

    private final LockoutPolicyItemConverter itemConverter;

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
