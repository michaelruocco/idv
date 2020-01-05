package uk.co.idv.json.lockout.policy;

import uk.co.idv.json.lockout.policy.hard.HardLockoutPolicyJsonNodeConverter;
import uk.co.idv.json.lockout.policy.nonlocking.NonLockingLockoutPolicyJsonNodeConverter;

public class DefaultLockoutPolicyDeserializer extends LockoutPolicyDeserializer {

    public DefaultLockoutPolicyDeserializer() {
        super(
                new NonLockingLockoutPolicyJsonNodeConverter(),
                new HardLockoutPolicyJsonNodeConverter()
        );
    }

}
