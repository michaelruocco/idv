package uk.co.idv.domain.entities.lockout.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevelMother;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SoftLockoutPolicyTest {

    private static final UUID ID = UUID.randomUUID();
    private static final LockoutLevel LEVEL = LockoutLevelMother.defaultLockoutLevel();
    private static final RecordAttemptStrategy RECORD_ATTEMPT_STRATEGY = new RecordEveryAttempt();
    private static final SoftLockIntervals INTERVALS = new SoftLockIntervals();

    private final SoftLockoutPolicy policy = new SoftLockoutPolicy(
            ID,
            LEVEL,
            RECORD_ATTEMPT_STRATEGY,
            INTERVALS
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
        assertThat(policy.getLockoutType()).isEqualTo(SoftLockoutStateCalculator.TYPE);
    }

    @Test
    void shouldReturnStateCalculator() {
        assertThat(policy.getStateCalculator()).isInstanceOf(SoftLockoutStateCalculator.class);
    }

    @Test
    void shouldReturnIntervals() {
        assertThat(policy.getIntervals()).isEqualTo(INTERVALS);
    }

}
