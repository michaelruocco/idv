package uk.co.mruoc.idv.lockout.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.domain.model.activity.OnlinePurchase;
import uk.co.mruoc.idv.domain.model.channel.Rsa;
import uk.co.mruoc.idv.identity.domain.model.CreditCardNumber;
import uk.co.mruoc.idv.identity.domain.model.DebitCardNumber;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RsaMaxAttemptsLockoutPolicyParametersTest {

    private final MaxAttemptsLockoutPolicyParameters parameters = new RsaMaxAttemptsLockoutPolicyParameters();

    @Test
    void shouldReturnId() {
        final UUID id = UUID.randomUUID();

        final MaxAttemptsLockoutPolicyParameters parametersWithId = new RsaMaxAttemptsLockoutPolicyParameters(id);

        assertThat(parametersWithId.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnRecordEveryAttemptStrategyType() {
        assertThat(parameters.getRecordAttemptStrategyType()).isEqualTo(RecordEveryAttempt.TYPE);
    }


    @Test
    void shouldReturnRsaChannelId() {
        assertThat(parameters.getChannelIds()).containsExactly(Rsa.ID);
    }

    @Test
    void shouldReturnOnlinePurchaseActivityName() {
        assertThat(parameters.getActivityNames()).containsExactly(OnlinePurchase.NAME);
    }

    @Test
    void shouldReturnCreditAndDebitCardAliasTypes() {
        assertThat(parameters.getAliasTypes()).containsExactly(CreditCardNumber.TYPE, DebitCardNumber.TYPE);
    }

    @Test
    void shouldReturnMaxNumberOfAttempts() {
        assertThat(parameters.getMaxNumberOfAttempts()).isEqualTo(3);
    }

}
