package uk.co.idv.json.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevelMother;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultLockoutPolicyDtoTest {

    private final LockoutPolicyDto parameters = new CustomLockoutPolicyDto();

    @Test
    void shouldReturnId() {
        assertThat(parameters.getId()).isEqualTo(CustomLockoutPolicyDto.ID);
    }

    @Test
    void shouldReturnLockoutType() {
        assertThat(parameters.getLockoutType()).isEqualTo(CustomLockoutPolicyDto.LOCKOUT_TYPE);
    }

    @Test
    void shouldReturnRecordAttemptStrategyType() {
        assertThat(parameters.getRecordAttempts()).isEqualTo(CustomLockoutPolicyDto.RECORD_ATTEMPTS);
    }

    @Test
    void shouldReturnLockoutLevel() {
        assertThat(parameters.getLockoutLevel()).isEqualTo(CustomLockoutPolicyDto.LOCKOUT_LEVEL);
    }

    private static class CustomLockoutPolicyDto extends DefaultLockoutPolicyDto {

        private static final UUID ID = UUID.fromString("0addd5b5-24ef-4298-9ebd-ed6f506f59c2");
        private static final String LOCKOUT_TYPE = "lockout-type";
        private static final String RECORD_ATTEMPTS = "record-attempt-strategy-type";
        private static final LockoutLevel LOCKOUT_LEVEL = lockoutLevel();

        private CustomLockoutPolicyDto() {
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
