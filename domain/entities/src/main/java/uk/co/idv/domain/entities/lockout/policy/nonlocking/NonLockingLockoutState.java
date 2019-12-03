package uk.co.idv.domain.entities.lockout.policy.nonlocking;

import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.state.NotLockedState;

public class NonLockingLockoutState extends NotLockedState {

    public NonLockingLockoutState(final VerificationAttempts attempts) {
        super("non locking policy", attempts);
    }

}
