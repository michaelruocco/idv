package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.document.Item;
import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.utils.json.converter.JsonConverter;

@RequiredArgsConstructor
public class LockoutPolicyItemConverter {

    private final JsonConverter jsonConverter;

    public LockoutPolicy toPolicy(final Item item) {
        return jsonConverter.toObject(item.getJSON("body"), LockoutPolicy.class);
    }

    public Item toItem(final LockoutPolicy policy) {
        return new Item()
                .withPrimaryKey("id", policy.getId().toString())
                .with("channelId", policy.getLevel().getChannelId())
                .with("lockoutType", policy.getType())
                .withJSON("body", jsonConverter.toJson(policy));
    }

}
