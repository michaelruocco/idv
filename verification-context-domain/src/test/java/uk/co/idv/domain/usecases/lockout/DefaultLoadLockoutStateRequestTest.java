package uk.co.idv.domain.usecases.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.Alias;
import uk.co.idv.domain.entities.identity.AliasesMother;
import uk.co.idv.domain.usecases.lockout.DefaultLoadLockoutStateRequest.DefaultLoadLockoutStateRequestBuilder;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultLoadLockoutStateRequestTest {

    private final DefaultLoadLockoutStateRequestBuilder builder = DefaultLoadLockoutStateRequest.builder();

    @Test
    void shouldReturnChannelId() {
        final String channelId = "channelId";

        final LockoutStateRequest request = builder.channelId(channelId).build();

        assertThat(request.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldReturnActivityName() {
        final String activityName = "activityName";

        final LockoutStateRequest request = builder.activityName(activityName).build();

        assertThat(request.getActivityName()).isEqualTo(activityName);
    }

    @Test
    void shouldReturnTimestamp() {
        final Instant timestamp = Instant.now();

        final LockoutStateRequest request = builder.timestamp(timestamp).build();

        assertThat(request.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    void shouldReturnAlias() {
        final Alias alias = AliasesMother.creditCardNumber();

        final LockoutStateRequest request = builder.alias(alias).build();

        assertThat(request.getAlias()).isEqualTo(alias);
    }

    @Test
    void shouldReturnAliasType() {
        final Alias alias = AliasesMother.creditCardNumber();

        final LockoutStateRequest request = builder.alias(alias).build();

        assertThat(request.getAliasType()).isEqualTo(alias.getType());
    }

    @Test
    void shouldReturnIdvIdValue() {
        final UUID idvIdValue = UUID.randomUUID();

        final LockoutStateRequest request = builder.idvIdValue(idvIdValue).build();

        assertThat(request.getIdvIdValue()).isEqualTo(idvIdValue);
    }

}
