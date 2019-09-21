package uk.co.mruoc.idv.verificationcontext.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.verificationcontext.domain.service.CreateContextRequest.CreateContextRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CreateContextRequestTest {

    private final CreateContextRequestBuilder builder = CreateContextRequest.builder();

    @Test
    void shouldReturnChannel() {
        final Channel channel = mock(Channel.class);

        final CreateContextRequest request = builder.channel(channel).build();

        assertThat(request.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldReturnActivity() {
        final Activity activity = mock(Activity.class);

        final CreateContextRequest request = builder.activity(activity).build();

        assertThat(request.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldReturnCreated() {
        final Alias providedAlias = mock(Alias.class);

        final CreateContextRequest request = builder.providedAlias(providedAlias).build();

        assertThat(request.getProvidedAlias()).isEqualTo(providedAlias);
    }

}
