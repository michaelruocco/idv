package uk.co.mruoc.idv.lockout.domain.model.uk;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.domain.model.activity.OnlinePurchase;
import uk.co.mruoc.idv.domain.model.channel.Rsa;
import uk.co.mruoc.idv.lockout.domain.model.MaxAttemptsAliasLevelLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.RecordEveryAttempt;
import uk.co.mruoc.idv.lockout.domain.model.RsaMaxAttemptsAliasLevelLockoutPolicyParameters;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RsaMaxAttemptsAliasLevelLockoutPolicyParametersTest {

    private final String aliasType = "alias-type";

    private final MaxAttemptsAliasLevelLockoutPolicyParameters parameters = new uk.co.mruoc.idv.lockout.domain.model.RsaMaxAttemptsAliasLevelLockoutPolicyParameters(aliasType);

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
