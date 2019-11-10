package uk.co.idv.domain.entities.lockout.policy;

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
    void shouldReturnLockoutLevel() {
        assertThat(parameters.getLockoutLevel()).isEqualTo(CustomLockoutPolicyParameters.LOCKOUT_LEVEL);
    }

    private static class CustomLockoutPolicyParameters extends DefaultLockoutPolicyParameters {

        private static final UUID ID = UUID.fromString("0addd5b5-24ef-4298-9ebd-ed6f506f59c2");
        private static final String LOCKOUT_TYPE = "lockout-type";
        private static final String RECORD_ATTEMPT_STRATEGY_TYPE = "record-attempt-strategy-type";
        private static final LockoutLevel LOCKOUT_LEVEL = lockoutLevel();

        private CustomLockoutPolicyParameters() {
            super(ID,
                    LOCKOUT_TYPE,
                    RECORD_ATTEMPT_STRATEGY_TYPE,
                    LOCKOUT_LEVEL);
        }

        private static LockoutLevel lockoutLevel() {
            return DefaultLockoutLevel.builder()
                    .channelId("fake-channel")
                    .activityName("fake-activity")
                    .build();
        }

    }

}
