package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.document.Item;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.repository.dynamo.json.JsonConverter;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LockoutPolicyItemConverter {

    private final JsonConverter jsonConverter;

    public Collection<LockoutPolicy> toPolicies(final Iterable<Item> items) {
        return toPolicies(IterableUtils.toList(items));
    }

    public Collection<LockoutPolicy> toPolicies(final Collection<Item> items) {
        return items.stream()
                .map(this::toPolicy)
                .collect(Collectors.toList());
    }

    public LockoutPolicy toPolicy(final Item item) {
        return jsonConverter.toObject(item.getJSON("body"), LockoutPolicy.class);
    }

    public Item toItem(final LockoutPolicy policy) {
        return new Item()
                .withPrimaryKey("id", policy.getId().toString())
                .with("channelId", policy.getLockoutLevel().getChannelId())
                .with("lockoutType", policy.getLockoutType())
                .withJSON("body", jsonConverter.toJson(policy));
    }

}
