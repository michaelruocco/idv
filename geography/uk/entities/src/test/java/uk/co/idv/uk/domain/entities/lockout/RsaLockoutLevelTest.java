package uk.co.idv.uk.domain.entities.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.activity.OnlinePurchase;
import uk.co.idv.uk.domain.entities.channel.Rsa;

import static org.assertj.core.api.Assertions.assertThat;

class RsaLockoutLevelTest {

    private static final String ALIAS_TYPE = "alias-type";

    private final RsaLockoutLevel level = new RsaLockoutLevel(ALIAS_TYPE);

    @Test
    void shouldReturnRsaChannelId() {
        assertThat(level.getChannelId()).isEqualTo(Rsa.ID);
    }

    @Test
    void shouldReturnOnlinePurchaseActivityName() {
        assertThat(level.getActivityName()).isEqualTo(OnlinePurchase.NAME);
    }

    @Test
    void shouldBeAliasLevel() {
        assertThat(level.isAliasLevel()).isTrue();
    }

    @Test
    void shouldReturnAliasType() {
        assertThat(level.getAliasType()).isEqualTo(ALIAS_TYPE);
    }

}
