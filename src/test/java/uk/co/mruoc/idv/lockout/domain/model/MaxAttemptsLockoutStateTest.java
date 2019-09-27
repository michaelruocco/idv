package uk.co.mruoc.idv.lockout.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.service.MaxAttemptsLockoutState;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MaxAttemptsLockoutStateTest {

    private static final int MAX_NUMBER_OF_ATTEMPTS = 3;
    private final VerificationAttempts attempts = new FakeVerificationAttempts();

    private final MaxAttemptsLockoutState state = new MaxAttemptsLockoutState(attempts, MAX_NUMBER_OF_ATTEMPTS);

    @Test
    void shouldReturnIdFromAttempts() {
        assertThat(state.getId()).isEqualTo(attempts.getId());
    }

    @Test
    void shouldReturnIdvIdFromAttempts() {
        assertThat(state.getIdvId()).isEqualTo(attempts.getIdvId());
    }

    @Test
    void shouldReturnLockoutType() {
        assertThat(state.getType()).isEqualTo(LockoutType.MAX_ATTEMPTS);
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

        final LockoutState threeAttemptsState = new MaxAttemptsLockoutState(threeAttempts, MAX_NUMBER_OF_ATTEMPTS);

        assertThat(threeAttemptsState.isLocked()).isTrue();
    }

}
