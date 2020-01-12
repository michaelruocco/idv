package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.document.Item;
import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.repository.dynamo.json.JsonConverter;

@RequiredArgsConstructor
public class LockoutPolicyItemConverter {

    private final JsonConverter jsonConverter;

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
