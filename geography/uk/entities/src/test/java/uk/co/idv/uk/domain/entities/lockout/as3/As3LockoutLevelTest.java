package uk.co.idv.uk.domain.entities.lockout.as3;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.activity.Login;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.uk.domain.entities.channel.as3.As3;

import static org.assertj.core.api.Assertions.assertThat;

class As3LockoutLevelTest {

    private final LockoutLevel level = new As3LockoutLevel();

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
        assertThat(level.getAliasTypes()).containsExactly(LockoutLevel.ALL);
    }

}
