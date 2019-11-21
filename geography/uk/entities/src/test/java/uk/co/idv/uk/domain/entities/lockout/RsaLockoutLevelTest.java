package uk.co.idv.uk.domain.entities.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.activity.OnlinePurchase;
import uk.co.idv.domain.entities.identity.alias.CreditCardNumber;
import uk.co.idv.domain.entities.identity.alias.DebitCardNumber;
import uk.co.idv.uk.domain.entities.channel.Rsa;

import static org.assertj.core.api.Assertions.assertThat;

class RsaLockoutLevelTest {

    private final RsaLockoutLevel level = new RsaLockoutLevel();

    @Test
    void shouldReturnRsaChannelId() {
        assertThat(level.getChannelId()).isEqualTo(Rsa.ID);
    }

    @Test
    void shouldReturnOnlinePurchaseActivityName() {
        assertThat(level.getActivityNames()).containsExactly(OnlinePurchase.NAME);
    }

    @Test
    void shouldBeAliasLevel() {
        assertThat(level.isAliasLevel()).isTrue();
    }

    @Test
    void shouldReturnCreditAndDebitCardAliasTypes() {
        assertThat(level.getAliasTypes()).containsExactly(
                CreditCardNumber.TYPE,
                DebitCardNumber.TYPE
        );
    }

}
