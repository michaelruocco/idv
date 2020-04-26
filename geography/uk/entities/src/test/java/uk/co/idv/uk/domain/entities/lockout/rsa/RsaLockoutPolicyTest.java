package uk.co.idv.uk.domain.entities.lockout.rsa;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;

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
        final LockoutLevel level = policy.getLevel();

        assertThat(level).isInstanceOf(RsaLockoutLevel.class);
    }

    @Test
    void shouldReturnRsaLockoutStateCalculator() {
        final LockoutStateCalculator stateCalculator = policy.getStateCalculator();

        assertThat(stateCalculator).isInstanceOf(RsaLockoutStateCalculator.class);
    }

    @Test
    void shouldReturnRecordEveryAttemptStrategy() {
        final RecordAttemptStrategy strategy = policy.getRecordAttemptStrategy();

        assertThat(strategy).isInstanceOf(RecordEveryAttempt.class);
    }

}
