package uk.co.idv.uk.domain.entities.policy.sequence.rsa;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.activity.OnlinePurchase;
import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.uk.domain.entities.channel.Rsa;

import static org.assertj.core.api.Assertions.assertThat;

class RsaOnlinePurchasePolicyLevelTest {

    private final PolicyLevel level = new RsaOnlinePurchasePolicyLevel();

    @Test
    void shouldReturnRsaChannelId() {
        assertThat(level.getChannelId()).isEqualTo(Rsa.ID);
    }

    @Test
    void shouldReturnOnlinePurchaseActivityName() {
        assertThat(level.getActivityNames()).containsExactly(OnlinePurchase.NAME);
    }

    @Test
    void shouldNotBeAliasLevel() {
        assertThat(level.isAliasLevel()).isFalse();
    }

    @Test
    void shouldReturnAllAliasTypes() {
        assertThat(level.getAliasTypes()).containsExactly(PolicyLevel.ALL);
    }

}
