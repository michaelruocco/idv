package uk.co.idv.domain.entities.lockout.uk;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.activity.OnlinePurchase;
import uk.co.idv.domain.entities.channel.Rsa;
import uk.co.idv.domain.entities.lockout.policy.MaxAttemptsAliasLevelLockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RsaMaxAttemptsAliasLevelLockoutPolicyParametersTest {

    private final String aliasType = "alias-type";

    private final MaxAttemptsAliasLevelLockoutPolicyParameters parameters = new RsaMaxAttemptsAliasLevelLockoutPolicyParameters(aliasType);

    @Test
    void shouldReturnId() {
        final UUID id = UUID.randomUUID();

        final MaxAttemptsAliasLevelLockoutPolicyParameters parametersWithId = new RsaMaxAttemptsAliasLevelLockoutPolicyParameters(aliasType, id);

        assertThat(parametersWithId.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnRecordEveryAttemptStrategyType() {
        assertThat(parameters.getRecordAttemptStrategyType()).isEqualTo(RecordEveryAttempt.TYPE);
    }


    @Test
    void shouldReturnRsaChannelId() {
        assertThat(parameters.getChannelId()).isEqualTo(Rsa.ID);
    }

    @Test
    void shouldReturnOnlinePurchaseActivityName() {
        assertThat(parameters.getActivityName()).isEqualTo(OnlinePurchase.NAME);
    }

    @Test
    void shouldReturnCreditAndDebitCardAliasTypes() {
        assertThat(parameters.getAliasType()).isEqualTo(aliasType);
    }

    @Test
    void shouldReturnMaxNumberOfAttempts() {
        assertThat(parameters.getMaxNumberOfAttempts()).isEqualTo(3);
    }

}
