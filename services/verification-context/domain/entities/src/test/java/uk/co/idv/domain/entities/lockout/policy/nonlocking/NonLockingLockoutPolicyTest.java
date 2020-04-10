package uk.co.idv.domain.entities.lockout.policy.nonlocking;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevelMother;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordNever;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class NonLockingLockoutPolicyTest {

    private static final UUID ID = UUID.randomUUID();
    private static final LockoutLevel LEVEL = LockoutLevelMother.defaultLockoutLevel();

    private final LockoutPolicy policy = new NonLockingLockoutPolicy(ID, LEVEL);

    @Test
    void shouldReturnId() {
        assertThat(policy.getId()).isEqualTo(ID);
    }

    @Test
    void shouldReturnLockoutLevel() {
        assertThat(policy.getLockoutLevel()).isEqualTo(LEVEL);
    }

    @Test
    void shouldReturnRecordAttemptStrategyType() {
        assertThat(policy.getRecordAttemptStrategyType()).isEqualTo(RecordNever.TYPE);
    }

    @Test
    void shouldReturnRecordAttemptsStrategy() {
        assertThat(policy.getRecordAttemptStrategy()).isInstanceOf(RecordNever.class);
    }

    @Test
    void shouldReturnLockoutType() {
        assertThat(policy.getLockoutType()).isEqualTo(NonLockingLockoutStateCalculator.TYPE);
    }

    @Test
    void shouldReturnStateCalculator() {
        assertThat(policy.getStateCalculator()).isInstanceOf(NonLockingLockoutStateCalculator.class);
    }

}
