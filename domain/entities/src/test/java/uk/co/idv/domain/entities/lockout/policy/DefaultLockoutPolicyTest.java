package uk.co.idv.domain.entities.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.lockout.state.FakeCalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.state.FakeLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.attempt.FakeVerificationAttemptFailed;
import uk.co.idv.domain.entities.lockout.attempt.FakeVerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempt;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.state.LockoutStateCalculator;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DefaultLockoutPolicyTest {

    private final UUID id = UUID.randomUUID();
    private final RecordAttemptStrategy recordAttemptStrategy = mock(RecordAttemptStrategy.class);
    private final FakeLockoutLevel level = new FakeLockoutLevel();
    private final FakeLockoutStateCalculator stateCalculator = new FakeLockoutStateCalculator();

    private final LockoutPolicy policy = new DefaultLockoutPolicy(
            id,
            stateCalculator,
            level,
            recordAttemptStrategy
    );

    @Test
    void shouldReturnTrueIfLockoutRequestAppliesToPolicy() {
        final RecordAttemptRequest request = mock(RecordAttemptRequest.class);
        level.setAppliesTo(true);

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
        final VerificationAttempt matchingAliasAttempt = new FakeVerificationAttemptFailed(UUID.randomUUID(), AliasesMother.creditCardNumber());
        final VerificationAttempt differentAliasAttempt = new FakeVerificationAttemptFailed(UUID.randomUUID(), AliasesMother.creditCardNumber("4929992222222222"));
        final VerificationAttempts attempts = new FakeVerificationAttempts(matchingAliasAttempt, differentAliasAttempt);
        final CalculateLockoutStateRequest request = new FakeCalculateLockoutStateRequest();

        final VerificationAttempts resetAttempts = policy.reset(attempts, request);

        assertThat(resetAttempts).containsExactlyElementsOf(attempts);
    }

    @Test
    void shouldResetAttemptsWhenTheyApplyToParameters() {
        level.setAppliesTo(true);
        final VerificationAttempt matchingAliasAttempt = new FakeVerificationAttemptFailed(UUID.randomUUID(), AliasesMother.creditCardNumber());
        final VerificationAttempt differentAliasAttempt = new FakeVerificationAttemptFailed(UUID.randomUUID(), AliasesMother.creditCardNumber("4929992222222222"));
        final VerificationAttempts attempts = new FakeVerificationAttempts(matchingAliasAttempt, differentAliasAttempt);
        final CalculateLockoutStateRequest inputRequest = new FakeCalculateLockoutStateRequest();

        final VerificationAttempts resetAttempts = policy.reset(attempts, inputRequest);

        assertThat(resetAttempts).isEmpty();
    }

    @Test
    void shouldResetAttemptsByAliasWhenLockingAtAliasLevelTheyApplyToParameters() {
        level.setAppliesTo(true);
        level.setIncludesAlias(true);
        final VerificationAttempt matchingAliasAttempt = new FakeVerificationAttemptFailed(UUID.randomUUID(), AliasesMother.creditCardNumber());
        final VerificationAttempt differentAliasAttempt = new FakeVerificationAttemptFailed(UUID.randomUUID(), AliasesMother.creditCardNumber("4929992222222222"));
        final VerificationAttempts attempts = new FakeVerificationAttempts(matchingAliasAttempt, differentAliasAttempt);
        final CalculateLockoutStateRequest inputRequest = new FakeCalculateLockoutStateRequest();

        final VerificationAttempts resetAttempts = policy.reset(attempts, inputRequest);

        assertThat(resetAttempts).containsExactly(differentAliasAttempt);
    }

    @Test
    void shouldReturnId() {
        assertThat(policy.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnLockoutType() {
        assertThat(policy.getLockoutType()).isEqualTo(stateCalculator.getType());
    }

    @Test
    void shouldReturnLockoutLevelType() {
        assertThat(policy.getLockoutLevelType()).isEqualTo(level.getType());
    }

    @Test
    void shouldReturnRecordAttemptStrategyType() {
        String expectedType = "record-attempt-strategy-type";
        given(recordAttemptStrategy.getType()).willReturn(expectedType);

        String type = policy.getRecordAttemptStrategyType();

        assertThat(type).isEqualTo(expectedType);
    }

    @Test
    void shouldReturnLockoutLevel() {
        final LockoutLevel result = policy.getLockoutLevel();

        assertThat(result).isEqualTo(level);
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
