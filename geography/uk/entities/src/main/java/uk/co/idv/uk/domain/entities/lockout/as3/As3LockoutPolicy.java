package uk.co.idv.uk.domain.entities.lockout.as3;

import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutPolicy;

import java.util.UUID;

public class As3LockoutPolicy extends NonLockingLockoutPolicy {

    public As3LockoutPolicy(final UUID id) {
        super(id, new As3LockoutLevel());
    }

}
