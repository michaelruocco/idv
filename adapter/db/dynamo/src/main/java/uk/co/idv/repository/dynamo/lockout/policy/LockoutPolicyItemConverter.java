package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.document.Item;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;

public interface LockoutPolicyItemConverter {

    boolean supportsType(final String lockoutType);

    LockoutPolicy toPolicy(final Item item);

    Item toItem(final LockoutPolicy policy);

}
