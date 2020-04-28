package uk.co.idv.domain.usecases.verificationcontext.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.activity.ActivityMother;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.channel.ChannelMother;
import uk.co.idv.domain.entities.identity.IdentityMother;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest.LoadSequencesRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;

class LoadSequencesRequestTest {

    private final LoadSequencesRequestBuilder builder = LoadSequencesRequest.builder();

    @Test
    void shouldReturnChannel() {
        final Channel channel = ChannelMother.fake();

        final LoadSequencesRequest request = builder.channel(channel).build();

        assertThat(request.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldReturnChannelId() {
        final Channel channel = ChannelMother.fake();

        final LoadSequencesRequest request = builder.channel(channel).build();

        assertThat(request.getChannelId()).isEqualTo(channel.getId());
    }

    @Test
    void shouldReturnActivity() {
        final Activity activity = ActivityMother.fake();

        final LoadSequencesRequest request = builder.activity(activity).build();

        assertThat(request.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldReturnActivityName() {
        final Activity activity = ActivityMother.fake();

        final LoadSequencesRequest request = builder.activity(activity).build();

        assertThat(request.getActivityName()).isEqualTo(activity.getName());
    }

    @Test
    void shouldReturnProvidedAlias() {
        final Alias providedAlias = AliasesMother.creditCardNumber();

        final LoadSequencesRequest request = builder.providedAlias(providedAlias).build();

        assertThat(request.getProvidedAlias()).isEqualTo(providedAlias);
    }

    @Test
    void shouldReturnProvidedAliasType() {
        final Alias providedAlias = AliasesMother.creditCardNumber();

        final LoadSequencesRequest request = builder.providedAlias(providedAlias).build();

        assertThat(request.getAliasType()).isEqualTo(providedAlias.getType());
    }

    @Test
    void shouldReturnIdentity() {
        final Identity identity = IdentityMother.build();

        final LoadSequencesRequest request = builder.identity(identity).build();

        assertThat(request.getIdentity()).isEqualTo(identity);
    }

    @Test
    void shouldReturnMobileDevicesFromIdentity() {
        final Identity identity = IdentityMother.build();

        final LoadSequencesRequest request = builder.identity(identity).build();

        assertThat(request.getMobileDevices()).isEqualTo(identity.getMobileDevices());
    }

    @Test
    void shouldReturnAccountsFromIdentity() {
        final Identity identity = IdentityMother.build();

        final LoadSequencesRequest request = builder.identity(identity).build();

        assertThat(request.getAccounts()).isEqualTo(identity.getAccounts());
    }

    @Test
    void shouldReturnPhoneNumbersFromIdentity() {
        final Identity identity = IdentityMother.build();

        final LoadSequencesRequest request = builder.identity(identity).build();

        assertThat(request.getPhoneNumbers()).isEqualTo(identity.getPhoneNumbers());
    }

}
