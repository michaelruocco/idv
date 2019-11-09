package uk.co.idv.domain.entities.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.AliasesMother;
import uk.co.idv.domain.usecases.lockout.CalculateLockoutStateRequest;
import uk.co.idv.domain.usecases.lockout.FakeCalculateLockoutStateRequest;
import uk.co.idv.domain.usecases.lockout.RecordAttemptRequest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DefaultLockoutPolicyTest {

    private final RecordAttemptStrategy recordAttemptStrategy = mock(RecordAttemptStrategy.class);
    private final FakeLockoutPolicyParameters parameters = new FakeLockoutPolicyParameters();
    private final FakeLockoutStateCalculator stateCalculator = new FakeLockoutStateCalculator();

    private final LockoutPolicy policy = DefaultLockoutPolicy.builder()
            .recordAttemptStrategy(recordAttemptStrategy)
            .parameters(parameters)
            .stateCalculator(stateCalculator)
            .build();

    @Test
    void shouldReturnTrueIfLockoutRequestAppliesToPolicy() {
        final RecordAttemptRequest request = mock(RecordAttemptRequest.class);
        parameters.setAppliesTo(true);

        final boolean result = policy.appliesTo(request);

        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseIfLockoutRequestDoesNotApplyToPolicy() {
        final RecordAttemptRequest request = mock(RecordAttemptRequest.class);

        final boolean result = policy.appliesTo(request);

        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnShouldRecordAttemptFromStrategy() {
        final RecordAttemptRequest request = mock(RecordAttemptRequest.class);
        given(recordAttemptStrategy.shouldRecordAttempt(request)).willReturn(true);

        final boolean result = policy.shouldRecordAttempt(request);

        assertThat(result).isTrue();
    }

    @Test
    void shouldNotRemoveAttemptsWhenResettingIfNoneAreApplicable() {
        final VerificationAttempts attempts = new FakeVerificationAttempts();

        final VerificationAttempts resetAttempts = policy.reset(attempts, null);

        assertThat(resetAttempts).containsExactlyElementsOf(attempts);
    }

    @Test
    void shouldPassRemainingAttemptsAfterResetToStateCalculatorWhenResettingState() {
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest();
        parameters.setAppliesTo(true);

        policy.reset(request);

        final CalculateLockoutStateRequest resetCalculateStateRequest = stateCalculator.getLastCalculateStateRequest();
        assertThat(resetCalculateStateRequest.getAttempts()).isEmpty();
    }

    @Test
    void shouldResetAttemptsWhenTheyApplyToParameters() {
        stateCalculator.setLockoutStateToReturn(new FakeLockoutStateMaxAttempts());
        parameters.setAppliesTo(true);
        final CalculateLockoutStateRequest inputRequest = new FakeCalculateLockoutStateRequest();

        policy.reset(inputRequest);

        final CalculateLockoutStateRequest resetRequest = stateCalculator.getLastCalculateStateRequest();
        assertThat(resetRequest.getAttempts()).isEmpty();
    }

    @Test
    void shouldResetAttemptsByAliasWhenLockingAtAliasLevelTheyApplyToParameters() {
        stateCalculator.setLockoutStateToReturn(new FakeLockoutStateMaxAttempts());
        parameters.setAppliesTo(true);
        parameters.setAliasLevelLocking(true);
        final VerificationAttempt matchingAliasAttempt = new FakeVerificationAttemptFailed(UUID.randomUUID(), AliasesMother.creditCardNumber());
        final VerificationAttempt differentAliasAttempt = new FakeVerificationAttemptFailed(UUID.randomUUID(), AliasesMother.creditCardNumber("4929992222222222"));
        final CalculateLockoutStateRequest inputRequest = new FakeCalculateLockoutStateRequest(new FakeVerificationAttempts(matchingAliasAttempt, differentAliasAttempt));

        policy.reset(inputRequest);

        final CalculateLockoutStateRequest resetRequest = stateCalculator.getLastCalculateStateRequest();
        assertThat(resetRequest.getAttempts()).containsExactly(differentAliasAttempt);
    }

    @Test
    void shouldReturnCalculatedLockoutStateAfterReset() {
        final LockoutState expectedState = new FakeLockoutStateMaxAttempts();
        stateCalculator.setLockoutStateToReturn(expectedState);
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest();

        final LockoutState state = policy.reset(request);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldPassApplicableAttemptsWhenCalculatingState() {
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest();

        policy.calculateLockoutState(request);

        final CalculateLockoutStateRequest calculateStateRequest = stateCalculator.getLastCalculateStateRequest();
        assertThat(calculateStateRequest.getAttempts()).isEmpty();
    }

    @Test
    void shouldReturnCalculatedLockoutState() {
        final LockoutState expectedState = new FakeLockoutStateMaxAttempts();
        stateCalculator.setLockoutStateToReturn(expectedState);
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest();

        final LockoutState state = policy.calculateLockoutState(request);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldReturnParameters() {
        final LockoutPolicyParameters result = policy.getParameters();

        assertThat(result).isEqualTo(parameters);
    }

    @Test
    void shouldReturnStateCalculator() {
        final LockoutStateCalculator result = policy.getStateCalculator();

        assertThat(result).isEqualTo(stateCalculator);
    }

    @Test
    void shouldReturnRecordAttemptStrategy() {
        final RecordAttemptStrategy result = policy.getRecordAttemptStrategy();

        assertThat(result).isEqualTo(recordAttemptStrategy);
    }

}
