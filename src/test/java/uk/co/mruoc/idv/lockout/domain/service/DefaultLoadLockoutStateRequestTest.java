package uk.co.mruoc.idv.lockout.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.FakeCreditCardNumber;
import uk.co.mruoc.idv.lockout.domain.service.DefaultLoadLockoutStateRequest.DefaultLoadLockoutStateRequestBuilder;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultLoadLockoutStateRequestTest {

    private final DefaultLoadLockoutStateRequestBuilder builder = DefaultLoadLockoutStateRequest.builder();

    @Test
    void shouldReturnChannelId() {
        final String channelId = "channelId";

        final LoadLockoutStateRequest request = builder.channelId(channelId).build();

        assertThat(request.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldReturnActivityName() {
        final String activityName = "activityName";

        final LoadLockoutStateRequest request = builder.activityName(activityName).build();

        assertThat(request.getActivityName()).isEqualTo(activityName);
    }

    @Test
    void shouldReturnTimestamp() {
        final Instant timestamp = Instant.now();

        final LoadLockoutStateRequest request = builder.timestamp(timestamp).build();

        assertThat(request.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    void shouldReturnAlias() {
        final Alias alias = new FakeCreditCardNumber();

        final LoadLockoutStateRequest request = builder.alias(alias).build();

        assertThat(request.getAlias()).isEqualTo(alias);
    }

    @Test
    void shouldReturnAliasType() {
        final Alias alias = new FakeCreditCardNumber();

        final LoadLockoutStateRequest request = builder.alias(alias).build();

        assertThat(request.getAliasType()).isEqualTo(alias.getType());
    }

    @Test
    void shouldReturnIdvIdValue() {
        final UUID idvIdValue = UUID.randomUUID();

        final LoadLockoutStateRequest request = builder.idvIdValue(idvIdValue).build();

        assertThat(request.getIdvIdValue()).isEqualTo(idvIdValue);
    }

}