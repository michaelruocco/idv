package uk.co.mruoc.idv.verificationcontext.domain.service;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.identity.domain.model.Identity;
import uk.co.mruoc.idv.verificationcontext.domain.service.LoadSequenceRequest.LoadSequenceRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class LoadSequenceRequestTest {

    private final LoadSequenceRequestBuilder builder = LoadSequenceRequest.builder();

    @Test
    void shouldReturnChannel() {
        final Channel channel = mock(Channel.class);

        final LoadSequenceRequest request = builder.channel(channel).build();

        assertThat(request.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldReturnActivity() {
        final Activity activity = mock(Activity.class);

        final LoadSequenceRequest request = builder.activity(activity).build();

        assertThat(request.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldReturnCreated() {
        final Identity identity = mock(Identity.class);

        final LoadSequenceRequest request = builder.identity(identity).build();

        assertThat(request.getIdentity()).isEqualTo(identity);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(LoadSequenceRequest.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
