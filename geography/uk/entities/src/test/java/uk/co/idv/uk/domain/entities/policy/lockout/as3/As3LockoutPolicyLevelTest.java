package uk.co.idv.uk.domain.entities.policy.lockout.as3;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.activity.Login;
import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.uk.domain.entities.channel.As3;

import static org.assertj.core.api.Assertions.assertThat;

class As3LockoutPolicyLevelTest {

    private final PolicyLevel level = new As3LockoutPolicyLevel();

    @Test
    void shouldReturnRsaChannelId() {
        assertThat(level.getChannelId()).isEqualTo(As3.ID);
    }

    @Test
    void shouldReturnLoginActivityName() {
        assertThat(level.getActivityNames()).containsExactly(Login.NAME);
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
