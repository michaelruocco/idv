package uk.co.idv.uk.domain.entities.lockout.as3;

import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordNever;

import java.util.UUID;

public class As3LockoutPolicy extends DefaultLockoutPolicy {

    public As3LockoutPolicy(final UUID id) {
        super(
                id,
                new As3LockoutPolicyLevel(),
                new NonLockingStateCalculator(),
                new RecordNever()
        );
    }

}
