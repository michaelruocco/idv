package uk.co.idv.domain.entities.lockout.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequestMother;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.NotLockedState;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class RecurringSoftLockoutStateCalculatorTest {

    private final SoftLockInterval interval = SoftLockIntervalMother.build(2);
    private final SoftLockoutStateFactory stateFactory = mock(SoftLockoutStateFactory.class);
    private final RecurringSoftLockoutStateCalculator calculator = new RecurringSoftLockoutStateCalculator(interval, stateFactory);

    @Test
    void shouldReturnType() {
        assertThat(calculator.getType()).isEqualTo(RecurringSoftLockoutStateCalculator.TYPE);
    }

    @Test
    void shouldReturnInterval() {
        assertThat(calculator.getInterval()).isEqualTo(interval);
    }

    @Test
    void shouldReturnNotLockedStateIfNumberOfAttemptsIsNotDivisibleByIntervalNumberOfAttempts() {
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequestMother.withOneAttempt();

        final LockoutState state = calculator.calculate(request);

        assertThat(state).isInstanceOf(NotLockedState.class);
    }

    @Test
    void shouldPopulateAttemptsOnNotLockedState() {
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequestMother.withOneAttempt();

        final LockoutState state = calculator.calculate(request);

        assertThat(state.getAttempts()).isEqualTo(request.getAttempts());
    }

    @Test
    void shouldReturnLockoutStateFromStateFactoryIfNumberOfAttemptsIsDivisibleByIntervalNumberOfAttempts() {
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequestMother.defaultBuilder()
                .attempts(VerificationAttemptsMother.withNumberOfAttempts(interval.getNumberOfAttempts()))
                .build();
        final LockoutState expectedState = mock(LockoutState.class);
        given(stateFactory.build(interval.getDuration(), request)).willReturn(expectedState);

        final LockoutState state = calculator.calculate(request);

        assertThat(state).isEqualTo(expectedState);
    }

}
