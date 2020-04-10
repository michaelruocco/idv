package uk.co.idv.domain.entities.lockout.policy.hard;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevelMother;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class HardLockoutPolicyTest {

    private static final UUID ID = UUID.randomUUID();
    private static final LockoutLevel LEVEL = LockoutLevelMother.defaultLockoutLevel();
    private static final RecordAttemptStrategy RECORD_ATTEMPT_STRATEGY = new RecordEveryAttempt();
    private static final int MAX_NUMBER_OF_ATTEMPTS = 3;

    private final HardLockoutPolicy policy = new HardLockoutPolicy(
            ID,
            LEVEL,
            RECORD_ATTEMPT_STRATEGY,
            MAX_NUMBER_OF_ATTEMPTS
    );

    @Test
    void shouldReturnId() {
        assertThat(policy.getId()).isEqualTo(ID);
    }

    @Test
    void shouldReturnLockoutLevel() {
        assertThat(policy.getLockoutLevel()).isEqualTo(LEVEL);
    }

    @Test
    void shouldReturnRecordAttemptsStrategyType() {
        assertThat(policy.getRecordAttemptStrategyType()).isEqualTo(RECORD_ATTEMPT_STRATEGY.getType());
    }

    @Test
    void shouldReturnRecordAttemptsStrategy() {
        assertThat(policy.getRecordAttemptStrategy()).isEqualTo(RECORD_ATTEMPT_STRATEGY);
    }

    @Test
    void shouldReturnLockoutType() {
        assertThat(policy.getLockoutType()).isEqualTo(HardLockoutStateCalculator.TYPE);
    }

    @Test
    void shouldReturnStateCalculator() {
        assertThat(policy.getStateCalculator()).isInstanceOf(HardLockoutStateCalculator.class);
    }

    @Test
    void shouldReturnMaxNumberOfAttempts() {
        assertThat(policy.getMaxNumberOfAttempts()).isEqualTo(MAX_NUMBER_OF_ATTEMPTS);
    }

}
