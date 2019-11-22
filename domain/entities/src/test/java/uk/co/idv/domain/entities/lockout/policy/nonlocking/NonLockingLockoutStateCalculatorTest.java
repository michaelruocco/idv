package uk.co.idv.domain.entities.lockout.policy.nonlocking;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;

import static org.assertj.core.api.Assertions.assertThat;

class NonLockingLockoutStateCalculatorTest {

    private final LockoutStateCalculator calculator = new NonLockingLockoutStateCalculator();

    @Test
    void shouldReturnNotLocked() {
        final VerificationAttempts oneAttempt = VerificationAttemptsMother.oneAttempt();
        final CalculateLockoutStateRequest request = toCalculateRequest(oneAttempt);

        final LockoutState state = calculator.calculate(request);

        assertThat(state.isLocked()).isFalse();
    }

    @Test
    void shouldReturnNonLockingLockoutState() {
        final VerificationAttempts oneAttempt = VerificationAttemptsMother.oneAttempt();
        final CalculateLockoutStateRequest request = toCalculateRequest(oneAttempt);

        final LockoutState state = calculator.calculate(request);

        assertThat(state).isInstanceOf(NonLockingLockoutState.class);
    }

    @Test
    void shouldReturnType() {
        assertThat(calculator.getType()).isEqualTo("non-locking");
    }

    private static CalculateLockoutStateRequest toCalculateRequest(final VerificationAttempts attempts) {
        return CalculateLockoutStateRequest.builder()
                .attempts(attempts)
                .build();
    }

}
