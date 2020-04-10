package uk.co.idv.api.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.beantest.ApiBeanTester;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevelMother;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultLockoutPolicyAttributesTest {

    private final LockoutPolicyAttributes parameters = new CustomLockoutPolicyAttributes();

    @Test
    void shouldReturnId() {
        assertThat(parameters.getId()).isEqualTo(CustomLockoutPolicyAttributes.ID);
    }

    @Test
    void shouldReturnLockoutType() {
        assertThat(parameters.getLockoutType()).isEqualTo(CustomLockoutPolicyAttributes.LOCKOUT_TYPE);
    }

    @Test
    void shouldReturnRecordAttemptStrategyType() {
        assertThat(parameters.getRecordAttempts()).isEqualTo(CustomLockoutPolicyAttributes.RECORD_ATTEMPTS);
    }

    @Test
    void shouldReturnLockoutLevel() {
        assertThat(parameters.getLockoutLevel()).isEqualTo(CustomLockoutPolicyAttributes.LOCKOUT_LEVEL);
    }

    @Test
    void shouldTestBean() {
        new ApiBeanTester().testBean(DefaultLockoutPolicyAttributes.class);
    }

    private static class CustomLockoutPolicyAttributes extends DefaultLockoutPolicyAttributes {

        private static final UUID ID = UUID.fromString("0addd5b5-24ef-4298-9ebd-ed6f506f59c2");
        private static final String LOCKOUT_TYPE = "lockout-type";
        private static final String RECORD_ATTEMPTS = "record-attempt-strategy-type";
        private static final LockoutLevel LOCKOUT_LEVEL = lockoutLevel();

        private CustomLockoutPolicyAttributes() {
            super(ID,
                    LOCKOUT_TYPE,
                    RECORD_ATTEMPTS,
                    LOCKOUT_LEVEL);
        }

        private static LockoutLevel lockoutLevel() {
            return LockoutLevelMother.defaultLockoutLevel();
        }

    }

}
