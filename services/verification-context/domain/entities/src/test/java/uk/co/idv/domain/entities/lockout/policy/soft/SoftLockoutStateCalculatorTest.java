package uk.co.idv.domain.entities.lockout.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequestMother;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.NotLockedState;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SoftLockoutStateCalculatorTest {

    private final SoftLockIntervals intervals = mock(SoftLockIntervals.class);
    private final SoftLockoutStateFactory stateFactory = mock(SoftLockoutStateFactory.class);
    private final SoftLockoutStateCalculator calculator = new SoftLockoutStateCalculator(intervals, stateFactory);

    @Test
    void shouldReturnType() {
        assertThat(calculator.getType()).isEqualTo(SoftLockoutStateCalculator.TYPE);
    }

    @Test
    void shouldReturnIntervals() {
        assertThat(calculator.getIntervals()).isEqualTo(intervals);
    }

    @Test
    void shouldReturnNotLockedStateIfIntervalNotFound() {
        final VerificationAttempts attempts = VerificationAttemptsMother.oneAttempt();
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequestMother.defaultBuilder()
                .attempts(attempts)
                .build();
        given(intervals.findInterval(attempts.size())).willReturn(Optional.empty());

        final LockoutState state = calculator.calculate(request);

        assertThat(state).isInstanceOf(NotLockedState.class);
    }

    @Test
    void shouldPopulateAttemptsOnNotLockedState() {
        final VerificationAttempts attempts = VerificationAttemptsMother.oneAttempt();
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequestMother.defaultBuilder()
                .attempts(attempts)
                .build();
        given(intervals.findInterval(attempts.size())).willReturn(Optional.empty());

        final LockoutState state = calculator.calculate(request);

        assertThat(state.getAttempts()).isEqualTo(request.getAttempts());
    }

    @Test
    void shouldReturnLockoutStateFromStateFactoryIfIntervalFound() {
        final SoftLockInterval interval = SoftLockIntervalMother.oneAttempt();
        final VerificationAttempts attempts = VerificationAttemptsMother.withNumberOfAttempts(interval.getNumberOfAttempts());
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequestMother.defaultBuilder()
                .attempts(attempts)
                .build();
        given(intervals.findInterval(attempts.size())).willReturn(Optional.of(interval));
        final LockoutState expectedLockoutState = mock(LockoutState.class);
        given(stateFactory.build(interval.getDuration(), request)).willReturn(expectedLockoutState);

        final LockoutState state = calculator.calculate(request);

        assertThat(state).isEqualTo(expectedLockoutState);
    }

}
