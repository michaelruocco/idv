package uk.co.idv.uk.domain.entities.lockout.as3;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordNever;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class As3LockoutPolicyTest {

    private static final UUID ID = UUID.randomUUID();

    private final LockoutPolicy policy = new As3LockoutPolicy(ID);

    @Test
    void shouldReturnId() {
        assertThat(policy.getId()).isEqualTo(ID);
    }

    @Test
    void shouldReturnAs3LockoutLevel() {
        final PolicyLevel level = policy.getLevel();

        assertThat(level).isInstanceOf(As3LockoutPolicyLevel.class);
    }

    @Test
    void shouldReturnNonLockingLockoutStateCalculator() {
        final LockoutStateCalculator stateCalculator = policy.getStateCalculator();

        assertThat(stateCalculator).isInstanceOf(NonLockingStateCalculator.class);
    }

    @Test
    void shouldReturnRecordNeverStrategy() {
        final RecordAttemptStrategy strategy = policy.getRecordAttemptStrategy();

        assertThat(strategy).isInstanceOf(RecordNever.class);
    }

}
