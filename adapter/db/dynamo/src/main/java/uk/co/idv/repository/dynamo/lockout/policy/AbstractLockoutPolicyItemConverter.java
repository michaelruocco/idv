package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.document.Item;
import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.repository.dynamo.json.JsonConverter;

@RequiredArgsConstructor
public abstract class AbstractLockoutPolicyItemConverter<T extends LockoutPolicy> implements LockoutPolicyItemConverter {

    private final String supportedType;
    private final Class<T> policyType;
    private final JsonConverter jsonConverter;

    @Override
    public boolean supportsType(final String type) {
        return supportedType.equals(type);
    }

    @Override
    public LockoutPolicy toPolicy(final Item item) {
        return jsonConverter.toObject(item.getJSON("body"), policyType);
    }

    @Override
    public Item toItem(final LockoutPolicy policy) {
        return new Item()
                .withPrimaryKey("id", policy.getId().toString())
                .with("channelId", policy.getLockoutLevel().getChannelId())
                .with("lockoutType", policy.getLockoutType())
                .withJSON("body", jsonConverter.toJson(policy));
    }

}
