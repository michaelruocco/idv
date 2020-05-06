package uk.co.idv.uk.domain.entities.policy.lockout.rsa;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;
import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RsaLockoutPolicyTest {

    private static final UUID ID = UUID.randomUUID();

    private final LockoutPolicy policy = new RsaLockoutPolicy(ID);

    @Test
    void shouldReturnId() {
        assertThat(policy.getId()).isEqualTo(ID);
    }

    @Test
    void shouldReturnRsaLockoutLevel() {
        final PolicyLevel level = policy.getLevel();

        assertThat(level).isInstanceOf(RsaLockoutPolicyLevel.class);
    }

    @Test
    void shouldReturnHardLockoutStateCalculatorWithMaxNumberOfAttemptsThree() {
        final LockoutStateCalculator stateCalculator = policy.getStateCalculator();

        assertThat(stateCalculator).isInstanceOf(RsaLockoutStateCalculator.class);
    }

    @Test
    void shouldReturnRecordEveryAttemptStrategy() {
        final RecordAttemptStrategy strategy = policy.getRecordAttemptStrategy();

        assertThat(strategy).isInstanceOf(RecordEveryAttempt.class);
    }

}
