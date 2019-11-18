package uk.co.idv.uk.domain.entities.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.AliasLockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RsaLockoutPolicyTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String ALIAS_TYPE = "alias-type";

    private final LockoutPolicy policy = new RsaLockoutPolicy(ID, ALIAS_TYPE);

    @Test
    void shouldReturnId() {
        assertThat(policy.getId()).isEqualTo(ID);
    }

    @Test
    void shouldReturnLockoutLevelWithLockoutLevelTypeAtAliasLevel() {
        final LockoutLevel level = policy.getLockoutLevel();

        assertThat(level).isInstanceOf(AliasLockoutLevel.class);
    }

    @Test
    void shouldSetAliasTypeOnAliasLockoutLevel() {
        final AliasLockoutLevel level = (AliasLockoutLevel) policy.getLockoutLevel();

        assertThat(level.getAliasType()).isEqualTo(ALIAS_TYPE);
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
