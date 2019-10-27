package uk.co.mruoc.idv.lockout.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.service.CalculateLockoutStateRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class MaxAttemptsLockoutStateCalculatorTest {

    private static final int MAX_NUMBER_OF_ATTEMPTS = 3;

    private final LockoutStateCalculator calculator = new MaxAttemptsLockoutStateCalculator(MAX_NUMBER_OF_ATTEMPTS);

    @Test
    void shouldReturnNotLockedIfNumberOfAttemptsIsLessThanMaxNumberOfAttempts() {
        final VerificationAttempts oneAttempt = buildOneAttempt();
        final CalculateLockoutStateRequest request = toCalculateRequest(oneAttempt);

        final LockoutState state = calculator.calculate(request);

        assertThat(state.isLocked()).isFalse();
    }

    @Test
    void shouldReturnLockedIfNumberOfAttemptsIsEqualToMaxNumberOfAttempts() {
        final VerificationAttempts attempts = buildAttemptsOfSize(MAX_NUMBER_OF_ATTEMPTS);
        final CalculateLockoutStateRequest request = toCalculateRequest(attempts);

        final LockoutState state = calculator.calculate(request);

        assertThat(state.isLocked()).isTrue();
    }

    @Test
    void shouldReturnLockedIfNumberOfAttemptsIsGreaterThanMaxNumberOfAttempts() {
        final VerificationAttempts attempts = buildAttemptsOfSize(MAX_NUMBER_OF_ATTEMPTS + 1);
        final CalculateLockoutStateRequest request = toCalculateRequest(attempts);

        final LockoutState state = calculator.calculate(request);

        assertThat(state.isLocked()).isTrue();
    }

    @Test
    void shouldReturnMaxAttemptsLockoutState() {
        final VerificationAttempts oneAttempt = buildOneAttempt();
        final CalculateLockoutStateRequest request = toCalculateRequest(oneAttempt);

        final LockoutState state = calculator.calculate(request);

        assertThat(state).isInstanceOf(LockoutStateMaxAttempts.class);
    }

    @Test
    void shouldReturnMaxNumberOfAttempts() {
        final VerificationAttempts oneAttempt = buildOneAttempt();
        final CalculateLockoutStateRequest request = toCalculateRequest(oneAttempt);

        final LockoutStateMaxAttempts state = (LockoutStateMaxAttempts) calculator.calculate(request);

        assertThat(state.getMaxNumberOfAttempts()).isEqualTo(MAX_NUMBER_OF_ATTEMPTS);
    }

    @Test
    void shouldReturnNumberOfAttemptsRemaining() {
        final VerificationAttempts oneAttempt = buildOneAttempt();
        final CalculateLockoutStateRequest request = toCalculateRequest(oneAttempt);

        final LockoutStateMaxAttempts state = (LockoutStateMaxAttempts) calculator.calculate(request);

        assertThat(state.getNumberOfAttemptsRemaining()).isEqualTo(MAX_NUMBER_OF_ATTEMPTS - oneAttempt.size());
    }

    @Test
    void shouldReturnZeroAttemptsRemainingIfLocked() {
        final VerificationAttempts attempts = buildAttemptsOfSize(MAX_NUMBER_OF_ATTEMPTS);
        final CalculateLockoutStateRequest request = toCalculateRequest(attempts);

        final LockoutStateMaxAttempts state = (LockoutStateMaxAttempts) calculator.calculate(request);

        assertThat(state.getNumberOfAttemptsRemaining()).isEqualTo(0);
    }

    private static VerificationAttempts buildOneAttempt() {
        return buildAttemptsOfSize(1);
    }

    private static VerificationAttempts buildAttemptsOfSize(final int numberOfAttempts) {
        final Collection<VerificationAttempt> attempts = new ArrayList<>();
        for (int i = 0; i < numberOfAttempts; i++) {
            attempts.add(mock(VerificationAttempt.class));
        }
        return new FakeVerificationAttempts(Collections.unmodifiableCollection(attempts));
    }

    private static CalculateLockoutStateRequest toCalculateRequest(final VerificationAttempts attempts) {
        return CalculateLockoutStateRequest.builder()
                .attempts(attempts)
                .build();
    }

}
