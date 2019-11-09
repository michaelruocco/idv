package uk.co.idv.domain.entities.lockout;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultLockoutPolicyParametersTest {

    private final LockoutPolicyParameters parameters = new CustomLockoutPolicyParameters();

    @Test
    void shouldReturnId() {
        assertThat(parameters.getId()).isEqualTo(CustomLockoutPolicyParameters.ID);
    }

    @Test
    void shouldReturnLockoutType() {
        assertThat(parameters.getLockoutType()).isEqualTo(CustomLockoutPolicyParameters.LOCKOUT_TYPE);
    }

    @Test
    void shouldReturnRecordAttemptStrategyType() {
        assertThat(parameters.getRecordAttemptStrategyType()).isEqualTo(CustomLockoutPolicyParameters.RECORD_ATTEMPT_STRATEGY_TYPE);
    }

    @Test
    void shouldReturnChannelId() {
        assertThat(parameters.getChannelId()).isEqualTo(CustomLockoutPolicyParameters.CHANNEL_ID);
    }

    @Test
    void shouldReturnActivityName() {
        assertThat(parameters.getActivityName()).isEqualTo(CustomLockoutPolicyParameters.ACTIVITY_NAME);
    }

    @Test
    void shouldReturnIsAliasLevelFalse() {
        assertThat(parameters.isAliasLevel()).isFalse();
    }

    @Test
    void shouldReturnAppliesToTrueIfChannelAndActivityMatch() {
        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId(CustomLockoutPolicyParameters.CHANNEL_ID)
                .activityName(CustomLockoutPolicyParameters.ACTIVITY_NAME)
                .build();

        final boolean applies = parameters.appliesTo(request);

        assertThat(applies).isTrue();
    }

    @Test
    void shouldReturnAppliesToFalseIfChannelIdDoesNotMatch() {
        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId("other-channel")
                .activityName(CustomLockoutPolicyParameters.ACTIVITY_NAME)
                .build();

        final boolean applies = parameters.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldReturnAppliesToFalseIfActivityNameDoesNotMatch() {
        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId(CustomLockoutPolicyParameters.CHANNEL_ID)
                .activityName("other-activity")
                .build();

        final boolean applies = parameters.appliesTo(request);

        assertThat(applies).isFalse();
    }

    private static class CustomLockoutPolicyParameters extends DefaultLockoutPolicyParameters {

        private static final UUID ID = UUID.fromString("0addd5b5-24ef-4298-9ebd-ed6f506f59c2");
        private static final String LOCKOUT_TYPE = "lockout-type";
        private static final String RECORD_ATTEMPT_STRATEGY_TYPE = "record-attempt-strategy-type";
        private static final String CHANNEL_ID = "fake-channel";
        private static final String ACTIVITY_NAME = "fake-activity";

        private CustomLockoutPolicyParameters() {
            super(ID,
                    LOCKOUT_TYPE,
                    RECORD_ATTEMPT_STRATEGY_TYPE,
                    CHANNEL_ID,
                    ACTIVITY_NAME);
        }

    }

}
