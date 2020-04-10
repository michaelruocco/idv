package uk.co.idv.domain.entities.lockout.policy.hard;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;

import static org.assertj.core.api.Assertions.assertThat;

class HardLockoutStateCalculatorTest {

    private static final int MAX_NUMBER_OF_ATTEMPTS = 3;

    private final HardLockoutStateCalculator calculator = new HardLockoutStateCalculator(MAX_NUMBER_OF_ATTEMPTS);

    @Test
    void shouldReturnNotLockedIfNumberOfAttemptsIsLessThanMaxNumberOfAttempts() {
        final VerificationAttempts oneAttempt = VerificationAttemptsMother.oneAttempt();
        final CalculateLockoutStateRequest request = toCalculateRequest(oneAttempt);

        final LockoutState state = calculator.calculate(request);

        assertThat(state.isLocked()).isFalse();
    }

    @Test
    void shouldReturnLockedIfNumberOfAttemptsIsEqualToMaxNumberOfAttempts() {
        final VerificationAttempts attempts = VerificationAttemptsMother.withNumberOfAttempts(MAX_NUMBER_OF_ATTEMPTS);
        final CalculateLockoutStateRequest request = toCalculateRequest(attempts);

        final LockoutState state = calculator.calculate(request);

        assertThat(state.isLocked()).isTrue();
    }

    @Test
    void shouldReturnLockedIfNumberOfAttemptsIsGreaterThanMaxNumberOfAttempts() {
        final VerificationAttempts attempts = VerificationAttemptsMother.withNumberOfAttempts(MAX_NUMBER_OF_ATTEMPTS + 1);
        final CalculateLockoutStateRequest request = toCalculateRequest(attempts);

        final LockoutState state = calculator.calculate(request);

        assertThat(state.isLocked()).isTrue();
    }

    @Test
    void shouldReturnHardLockoutState() {
        final VerificationAttempts oneAttempt = VerificationAttemptsMother.oneAttempt();
        final CalculateLockoutStateRequest request = toCalculateRequest(oneAttempt);

        final LockoutState state = calculator.calculate(request);

        assertThat(state).isInstanceOf(HardLockoutState.class);
    }

    @Test
    void shouldReturnMaxNumberOfAttemptsFromCalculatedState() {
        final VerificationAttempts oneAttempt = VerificationAttemptsMother.oneAttempt();
        final CalculateLockoutStateRequest request = toCalculateRequest(oneAttempt);

        final HardLockoutState state = calculator.calculate(request);

        assertThat(state.getMaxNumberOfAttempts()).isEqualTo(MAX_NUMBER_OF_ATTEMPTS);
    }

    @Test
    void shouldReturnNumberOfAttemptsRemaining() {
        final VerificationAttempts oneAttempt = VerificationAttemptsMother.oneAttempt();
        final CalculateLockoutStateRequest request = toCalculateRequest(oneAttempt);

        final HardLockoutState state = calculator.calculate(request);

        assertThat(state.getNumberOfAttemptsRemaining()).isEqualTo(MAX_NUMBER_OF_ATTEMPTS - oneAttempt.size());
    }

    @Test
    void shouldReturnZeroAttemptsRemainingIfLocked() {
        final VerificationAttempts attempts = VerificationAttemptsMother.withNumberOfAttempts(MAX_NUMBER_OF_ATTEMPTS);
        final CalculateLockoutStateRequest request = toCalculateRequest(attempts);

        final HardLockoutState state = calculator.calculate(request);

        assertThat(state.getNumberOfAttemptsRemaining()).isEqualTo(0);
    }

    @Test
    void shouldReturnType() {
        assertThat(calculator.getType()).isEqualTo(HardLockoutStateCalculator.TYPE);
    }

    @Test
    void shouldReturnMaxNumberOfAttempts() {
        assertThat(calculator.getMaxNumberOfAttempts()).isEqualTo(MAX_NUMBER_OF_ATTEMPTS);
    }

    private static CalculateLockoutStateRequest toCalculateRequest(final VerificationAttempts attempts) {
        return CalculateLockoutStateRequest.builder()
                .attempts(attempts)
                .build();
    }

}
