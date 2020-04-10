package uk.co.idv.json.lockout.policy;

import uk.co.idv.json.lockout.policy.hard.HardLockoutPolicyJsonNodeConverter;
import uk.co.idv.json.lockout.policy.nonlocking.NonLockingLockoutPolicyJsonNodeConverter;
import uk.co.idv.json.lockout.policy.soft.RecurringSoftLockoutPolicyJsonNodeConverter;
import uk.co.idv.json.lockout.policy.soft.SoftLockoutPolicyJsonNodeConverter;

public class DefaultLockoutPolicyDeserializer extends LockoutPolicyDeserializer {

    public DefaultLockoutPolicyDeserializer() {
        super(
                new NonLockingLockoutPolicyJsonNodeConverter(),
                new HardLockoutPolicyJsonNodeConverter(),
                new SoftLockoutPolicyJsonNodeConverter(),
                new RecurringSoftLockoutPolicyJsonNodeConverter()
        );
    }

}
