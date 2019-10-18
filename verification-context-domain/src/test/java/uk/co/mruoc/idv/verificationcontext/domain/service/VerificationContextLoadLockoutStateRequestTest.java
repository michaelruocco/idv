package uk.co.mruoc.idv.verificationcontext.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.model.activity.FakeActivity;
import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.domain.model.channel.FakeChannel;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.FakeCreditCardNumber;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.idv.verificationcontext.domain.service.VerificationContextLoadLockoutStateRequest.VerificationContextLoadLockoutStateRequestBuilder;
import static uk.co.mruoc.idv.verificationcontext.domain.service.VerificationContextLoadLockoutStateRequest.builder;

class VerificationContextLoadLockoutStateRequestTest {

    private final VerificationContextLoadLockoutStateRequestBuilder builder = builder();

    @Test
    void shouldReturnChannelId() {
        final Channel channel = new FakeChannel();

        final VerificationContextLoadLockoutStateRequest request = builder.channel(channel).build();

        assertThat(request.getChannelId()).isEqualTo(channel.getId());
    }

    @Test
    void shouldReturnActivityName() {
        final Activity activity = new FakeActivity();

        final VerificationContextLoadLockoutStateRequest request = builder.activity(activity).build();

        assertThat(request.getActivityName()).isEqualTo(activity.getName());
    }

    @Test
    void shouldReturnAlias() {
        final Alias alias = new FakeCreditCardNumber();

        final VerificationContextLoadLockoutStateRequest request = builder.alias(alias).build();

        assertThat(request.getAlias()).isEqualTo(alias);
    }


    @Test
    void shouldReturnAliasType() {
        final Alias alias = new FakeCreditCardNumber();

        final VerificationContextLoadLockoutStateRequest request = builder.alias(alias).build();

        assertThat(request.getAliasType()).isEqualTo(alias.getType());
    }

    @Test
    void shouldReturnTimestamp() {
        final Instant timestamp = Instant.now();

        final VerificationContextLoadLockoutStateRequest request = builder.timestamp(timestamp).build();

        assertThat(request.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    void shouldReturnIdvIdValue() {
        final UUID idvIdValue = UUID.randomUUID();

        final VerificationContextLoadLockoutStateRequest request = builder.idvIdValue(idvIdValue).build();

        assertThat(request.getIdvIdValue()).isEqualTo(idvIdValue);
    }

}
