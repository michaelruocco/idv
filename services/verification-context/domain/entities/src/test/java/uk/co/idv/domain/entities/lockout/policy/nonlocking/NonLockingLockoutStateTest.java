package uk.co.idv.domain.entities.lockout.policy.nonlocking;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;

import static org.assertj.core.api.Assertions.assertThat;

class NonLockingLockoutStateTest {

    private final VerificationAttempts attempts = VerificationAttemptsMother.oneAttempt();

    private final LockoutState state = new NonLockingLockoutState(attempts);

    @Test
    void shouldReturnIdFromAttempts() {
        assertThat(state.getId()).isEqualTo(attempts.getId());
    }

    @Test
    void shouldReturnIdvIdFromAttempts() {
        assertThat(state.getIdvId()).isEqualTo(attempts.getIdvId());
    }

    @Test
    void shouldReturnAttempts() {
        assertThat(state.getAttempts()).isEqualTo(attempts);
    }

    @Test
    void shouldNotBeLocked() {
        assertThat(state.isLocked()).isFalse();
    }

    @Test
    void shouldReturnNotLockedMessage() {
        assertThat(state.getMessage()).isEqualTo("non locking policy");
    }

}
