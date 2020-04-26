package uk.co.idv.domain.entities.lockout.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevelMother;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RecurringSoftLockoutPolicyTest {

    private static final UUID ID = UUID.randomUUID();
    private static final LockoutLevel LEVEL = LockoutLevelMother.defaultLockoutLevel();
    private static final RecordAttemptStrategy RECORD_ATTEMPT_STRATEGY = new RecordEveryAttempt();
    private static final SoftLockInterval INTERVAL = SoftLockIntervalMother.oneAttempt();

    private final RecurringSoftLockoutPolicy policy = new RecurringSoftLockoutPolicy(
            ID,
            LEVEL,
            RECORD_ATTEMPT_STRATEGY,
            INTERVAL
    );

    @Test
    void shouldReturnId() {
        assertThat(policy.getId()).isEqualTo(ID);
    }

    @Test
    void shouldReturnLockoutLevel() {
        assertThat(policy.getLevel()).isEqualTo(LEVEL);
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
        assertThat(policy.getType()).isEqualTo(RecurringSoftLockoutStateCalculator.TYPE);
    }

    @Test
    void shouldReturnStateCalculator() {
        assertThat(policy.getStateCalculator()).isInstanceOf(RecurringSoftLockoutStateCalculator.class);
    }

    @Test
    void shouldReturnInterval() {
        assertThat(policy.getInterval()).isEqualTo(INTERVAL);
    }

}
