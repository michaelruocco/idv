package uk.co.idv.domain.entities.lockout.policy.hard;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class HardLockoutStateTest {

    private static final int MAX_NUMBER_OF_ATTEMPTS = 3;
    private final VerificationAttempts attempts = VerificationAttemptsMother.oneAttempt();

    private final HardLockoutState state = new HardLockoutState(attempts, MAX_NUMBER_OF_ATTEMPTS);

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
    void shouldReturnNumberOfAttemptsRemaining() {
        assertThat(state.getNumberOfAttemptsRemaining()).isEqualTo(MAX_NUMBER_OF_ATTEMPTS - attempts.size());
    }

    @Test
    void shouldNotBeLockedIfNumberOfAttemptsRemainingIsGreaterThanZero() {
        assertThat(state.isLocked()).isFalse();
    }

    @Test
    void shouldBeLockedIfNumberOfAttemptsRemainingIsLessThanOrEqualToZero() {
        final VerificationAttempts threeAttempts = mock(VerificationAttempts.class);
        given(threeAttempts.size()).willReturn(3);

        final LockoutState lockedState = new HardLockoutState(threeAttempts, MAX_NUMBER_OF_ATTEMPTS);

        assertThat(lockedState.isLocked()).isTrue();
    }

    @Test
    void shouldReturnNotLockedMessageIfNotLocked() {
        assertThat(state.getMessage()).isEqualTo("2 attempts remaining");
    }

    @Test
    void shouldReturnLockedMessageIfLocked() {
        final VerificationAttempts threeAttempts = mock(VerificationAttempts.class);
        given(threeAttempts.size()).willReturn(MAX_NUMBER_OF_ATTEMPTS);

        final LockoutState lockedState = new HardLockoutState(threeAttempts, MAX_NUMBER_OF_ATTEMPTS);

        final String expectedMessage = String.format("maximum number of attempts [%d] reached", MAX_NUMBER_OF_ATTEMPTS);
        assertThat(lockedState.getMessage()).isEqualTo(expectedMessage);
    }

}
