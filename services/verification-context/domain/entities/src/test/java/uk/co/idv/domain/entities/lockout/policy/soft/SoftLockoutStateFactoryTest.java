package uk.co.idv.domain.entities.lockout.policy.soft;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.NotLockedState;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SoftLockoutStateFactoryTest {

    private final VerificationAttempts attempts = VerificationAttemptsMother.oneAttempt();
    private final Duration duration = Duration.ofHours(1);
    private final Instant lockedUntil = Instant.now();
    private final CalculateLockoutStateRequest request = mock(CalculateLockoutStateRequest.class);

    private final SoftLockoutStateFactory stateFactory = new SoftLockoutStateFactory();

    @BeforeEach
    void setUp() {
        given(request.calculateLockedUntil(duration)).willReturn(lockedUntil);
        given(request.getAttempts()).willReturn(attempts);
    }

    @Test
    void shouldReturnNotLockedStateIfAttemptNotIssuedBeforeLockedUntil() {
        given(request.wasIssuedBefore(lockedUntil)).willReturn(false);

        final LockoutState state = stateFactory.build(duration, request);

        assertThat(state).isInstanceOf(NotLockedState.class);
    }

    @Test
    void shouldPopulateAttemptsOnNotLockedState() {
        given(request.wasIssuedBefore(lockedUntil)).willReturn(false);

        final LockoutState state = stateFactory.build(duration, request);

        assertThat(state.getAttempts()).isEqualTo(attempts);
    }

    @Test
    void shouldReturnSoftLockoutStateIfAttemptIssuedBeforeLockedUntil() {
        given(request.wasIssuedBefore(lockedUntil)).willReturn(true);

        final LockoutState state = stateFactory.build(duration, request);

        assertThat(state).isInstanceOf(SoftLockoutState.class);
    }

    @Test
    void shouldPopulateAttemptsOnSoftLockoutState() {
        given(request.wasIssuedBefore(lockedUntil)).willReturn(true);

        final LockoutState state = stateFactory.build(duration, request);

        assertThat(state.getAttempts()).isEqualTo(attempts);
    }

    @Test
    void shouldPopulateDurationOnSoftLockoutState() {
        given(request.wasIssuedBefore(lockedUntil)).willReturn(true);

        final SoftLockoutState state = (SoftLockoutState) stateFactory.build(duration, request);

        assertThat(state.getDuration()).isEqualTo(duration);
    }

    @Test
    void shouldPopulateLockedUntilOnSoftLockoutState() {
        given(request.wasIssuedBefore(lockedUntil)).willReturn(true);

        final SoftLockoutState state = (SoftLockoutState) stateFactory.build(duration, request);

        assertThat(state.getLockedUntil()).isEqualTo(lockedUntil);
    }

}
