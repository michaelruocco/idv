package uk.co.mruoc.idv.verificationcontext.domain.service;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.model.activity.FakeActivity;
import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.domain.model.channel.FakeChannel;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.FakeIdvId;
import uk.co.mruoc.idv.verificationcontext.domain.service.CreateContextRequest.CreateContextRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;

class CreateContextRequestTest {

    private final CreateContextRequestBuilder builder = CreateContextRequest.builder();

    @Test
    void shouldReturnChannel() {
        final Channel channel = new FakeChannel();

        final CreateContextRequest request = builder.channel(channel).build();

        assertThat(request.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldReturnChannelId() {
        final Channel channel = new FakeChannel();

        final CreateContextRequest request = builder.channel(channel).build();

        assertThat(request.getChannelId()).isEqualTo(channel.getId());
    }

    @Test
    void shouldReturnActivity() {
        final Activity activity = new FakeActivity();

        final CreateContextRequest request = builder.activity(activity).build();

        assertThat(request.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldReturnActivityName() {
        final Activity activity = new FakeActivity();

        final CreateContextRequest request = builder.activity(activity).build();

        assertThat(request.getActivityName()).isEqualTo(activity.getName());
    }

    @Test
    void shouldReturnCreated() {
        final Alias providedAlias = new FakeIdvId();

        final CreateContextRequest request = builder.providedAlias(providedAlias).build();

        assertThat(request.getProvidedAlias()).isEqualTo(providedAlias);
    }

    @Test
    void shouldBeEqualIfAllValuesAreTheSame() {
        EqualsVerifier.forClass(CreateContextRequest.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
