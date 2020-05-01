package uk.co.idv.domain.entities.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequestMother;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.policy.PolicyLevel;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DefaultLockoutPolicyTest {

    private final UUID id = UUID.randomUUID();
    private final PolicyLevel level = mock(PolicyLevel.class);
    private final RecordAttemptStrategy recordAttemptStrategy = mock(RecordAttemptStrategy.class);
    private final LockoutStateCalculator stateCalculator = mock(LockoutStateCalculator.class);

    private final LockoutPolicy policy = new DefaultLockoutPolicy(
            id,
            level,
            stateCalculator,
            recordAttemptStrategy
    );

    @Test
    void shouldReturnTrueIfLockoutRequestAppliesToPolicy() {
        final RecordAttemptRequest request = mock(RecordAttemptRequest.class);
        given(level.appliesTo(request)).willReturn(true);

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
    void shouldReturnChannelIdFromLockoutLevel() {
        final String channelId = policy.getChannelId();

        assertThat(channelId).isEqualTo(level.getChannelId());
    }

    @Test
    void shouldIncludeApplicableAttemptsWhenCalculatingLockoutState() {
        final VerificationAttempts attempts = mock(VerificationAttempts.class);
        final CalculateLockoutStateRequest request = mock(CalculateLockoutStateRequest.class);
        given(request.getAttempts()).willReturn(attempts);
        final VerificationAttempts applicableAttempts = mock(VerificationAttempts.class);
        final CalculateLockoutStateRequest updatedRequest = mock(CalculateLockoutStateRequest.class);
        given(attempts.filterApplicable(level, request.getAlias())).willReturn(applicableAttempts);
        given(request.updateAttempts(applicableAttempts)).willReturn(updatedRequest);
        final LockoutState expectedState = mock(LockoutState.class);
        given(stateCalculator.calculate(updatedRequest)).willReturn(expectedState);

        final LockoutState state = policy.calculateState(request);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldRemoveApplicableAttemptsFromAttemptsWhenReset() {
        final VerificationAttempts attempts = mock(VerificationAttempts.class);
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequestMother.withAttempts(attempts);
        final VerificationAttempts applicableAttempts = mock(VerificationAttempts.class);
        given(attempts.filterApplicable(level, request.getAlias())).willReturn(applicableAttempts);
        final VerificationAttempts expectedResetAttempts = mock(VerificationAttempts.class);
        given(attempts.remove(applicableAttempts)).willReturn(expectedResetAttempts);

        final VerificationAttempts resetAttempts = policy.reset(request);

        assertThat(resetAttempts).isEqualTo(expectedResetAttempts);
    }

    @Test
    void shouldReturnId() {
        assertThat(policy.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnLockoutType() {
        assertThat(policy.getType()).isEqualTo(stateCalculator.getType());
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
        final PolicyLevel result = policy.getLevel();

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

    @Test
    void shouldReturnIsAliasLevel() {
        given(level.isAliasLevel()).willReturn(true);

        final boolean aliasLevel = policy.isAliasLevel();

        assertThat(aliasLevel).isTrue();
    }

}
