package uk.co.idv.domain.entities.lockout.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.FakeCalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.state.NotLockedState;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class RecurringSoftLockoutStateCalculatorTest {

    private final SoftLockInterval interval = new SoftLockInterval(2, Duration.ofMinutes(5));
    private final LockoutStateCalculator calculator = new RecurringSoftLockoutStateCalculator(interval);

    @Test
    void shouldReturnType() {
        assertThat(calculator.getType()).isEqualTo(RecurringSoftLockoutStateCalculator.TYPE);
    }

    @Test
    void shouldReturnNotLockedStateIfNumberOfAttemptsIsNotDivisableByIntervalNumberOfAttempts() {
        final VerificationAttempts attempts = VerificationAttemptsMother.oneAttempt();
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest(attempts);

        final LockoutState state = calculator.calculate(request);

        assertThat(state).isInstanceOf(NotLockedState.class);
    }

    @Test
    void shouldPopulateAttemptsOnNotLockedState() {
        final VerificationAttempts attempts = VerificationAttemptsMother.oneAttempt();
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest(attempts);

        final LockoutState state = calculator.calculate(request);

        assertThat(state.getAttempts()).isEqualTo(request.getAttempts());
    }

    @Test
    void shouldReturnSoftLockoutStateIfNumberOfAttemptsIsDivisableByIntervalNumberOfAttempts() {
        final VerificationAttempts attempts = VerificationAttemptsMother.withNumberOfAttempts(interval.getNumberOfAttempts() * 2);
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest(attempts);

        final LockoutState state = calculator.calculate(request);

        assertThat(state).isInstanceOf(SoftLockoutState.class);
    }

    @Test
    void shouldPopulateAttemptsOnSoftLockState() {
        final VerificationAttempts attempts = VerificationAttemptsMother.withNumberOfAttempts(interval.getNumberOfAttempts());
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest(attempts);

        final LockoutState state = calculator.calculate(request);

        assertThat(state.getAttempts()).isEqualTo(request.getAttempts());
    }

    @Test
    void shouldPopulateIntervalDurationOnSoftLockState() {
        final VerificationAttempts attempts = VerificationAttemptsMother.withNumberOfAttempts(interval.getNumberOfAttempts());
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest(attempts);

        final SoftLockoutState state = (SoftLockoutState) calculator.calculate(request);

        assertThat(state.getDuration()).isEqualTo(interval.getDuration());
    }

    @Test
    void shouldPopulateLockedUntilOnSoftLockState() {
        final VerificationAttempts attempts = VerificationAttemptsMother.withNumberOfAttempts(interval.getNumberOfAttempts());
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest(attempts);

        final SoftLockoutState state = (SoftLockoutState) calculator.calculate(request);

        final Instant mostRecentTimestamp = attempts.getMostRecentTimestamp();
        assertThat(state.getLockedUntil()).isEqualTo(mostRecentTimestamp.plus(interval.getDuration()));
    }

}