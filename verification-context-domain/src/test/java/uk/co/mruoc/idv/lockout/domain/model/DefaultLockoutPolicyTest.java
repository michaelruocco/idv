package uk.co.mruoc.idv.lockout.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.service.CalculateLockoutStateRequest;
import uk.co.mruoc.idv.lockout.domain.service.FakeCalculateLockoutStateRequest;
import uk.co.mruoc.idv.lockout.domain.service.RecordAttemptRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DefaultLockoutPolicyTest {

    @Test
    void shouldReturnTrueIfLockoutRequestAppliesToPolicy() {
        final RecordAttemptRequest request = mock(RecordAttemptRequest.class);
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .appliesToPolicy(lockoutRequest -> true)
                .build();

        final boolean result = policy.appliesTo(request);

        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseIfLockoutRequestDoesNotApplyToPolicy() {
        final RecordAttemptRequest request = mock(RecordAttemptRequest.class);
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .appliesToPolicy(lockoutRequest -> false)
                .build();

        final boolean result = policy.appliesTo(request);

        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnShouldRecordAttemptFromStrategy() {
        final RecordAttemptRequest request = mock(RecordAttemptRequest.class);
        final RecordAttemptStrategy strategy = mock(RecordAttemptStrategy.class);
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .recordAttemptStrategy(strategy)
                .build();
        given(strategy.shouldRecordAttempt(request)).willReturn(true);

        final boolean result = policy.shouldRecordAttempt(request);

        assertThat(result).isTrue();
    }

    @Test
    void shouldRemoveAllApplicableAttemptsWhenResetting() {
        final VerificationAttempts attempts = new FakeVerificationAttempts();
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .appliesToPolicy(lockoutRequest -> true)
                .build();

        final VerificationAttempts resetAttempts = policy.reset(attempts);

        assertThat(resetAttempts).isEmpty();
    }

    @Test
    void shouldNotRemoveAttemptsWhenResettingIfNoneAreApplicable() {
        final VerificationAttempts attempts = new FakeVerificationAttempts();
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .appliesToPolicy(lockoutRequest -> false)
                .build();

        final VerificationAttempts resetAttempts = policy.reset(attempts);

        assertThat(resetAttempts).containsExactlyElementsOf(attempts);
    }

    @Test
    void shouldPassRemainingAttemptsAfterResetToStateCalculatorWhenResettingState() {
        final FakeLockoutStateCalculator stateCalculator = new FakeLockoutStateCalculator();
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .appliesToPolicy(lockoutRequest -> true)
                .stateCalculator(stateCalculator)
                .build();
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest();

        policy.reset(request);

        final CalculateLockoutStateRequest resetCalculateStateRequest = stateCalculator.getLastCalculateStateRequest();
        assertThat(resetCalculateStateRequest.getAttempts()).isEmpty();
    }

    @Test
    void shouldReturnCalculatedLockoutStateAfterReset() {
        final FakeLockoutStateCalculator stateCalculator = new FakeLockoutStateCalculator();
        final LockoutState expectedState = new FakeLockoutStateMaxAttempts();
        stateCalculator.setLockoutStateToReturn(expectedState);
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .appliesToPolicy(lockoutRequest -> true)
                .stateCalculator(stateCalculator)
                .build();
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest();

        final LockoutState state = policy.reset(request);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldPassApplicableAttemptsWhenCalculatingState() {
        final FakeLockoutStateCalculator stateCalculator = new FakeLockoutStateCalculator();
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .appliesToPolicy(lockoutRequest -> false)
                .stateCalculator(stateCalculator)
                .build();
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest();

        policy.calculateLockoutState(request);

        final CalculateLockoutStateRequest calculateStateRequest = stateCalculator.getLastCalculateStateRequest();
        assertThat(calculateStateRequest.getAttempts()).isEmpty();
    }

    @Test
    void shouldReturnCalculatedLockoutState() {
        final FakeLockoutStateCalculator stateCalculator = new FakeLockoutStateCalculator();
        final LockoutState expectedState = new FakeLockoutStateMaxAttempts();
        stateCalculator.setLockoutStateToReturn(expectedState);
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .appliesToPolicy(lockoutRequest -> false)
                .stateCalculator(stateCalculator)
                .build();
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest();

        final LockoutState state = policy.calculateLockoutState(request);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldReturnParameters() {
        final LockoutPolicyParameters expectedParameters = mock(LockoutPolicyParameters.class);
        final LockoutPolicy policy = DefaultLockoutPolicy.builder()
                .parameters(expectedParameters)
                .build();

        final LockoutPolicyParameters parameters = policy.getParameters();

        assertThat(parameters).isEqualTo(expectedParameters);
    }

}
