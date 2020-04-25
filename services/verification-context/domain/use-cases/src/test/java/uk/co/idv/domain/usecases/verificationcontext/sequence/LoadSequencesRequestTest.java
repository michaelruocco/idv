package uk.co.idv.domain.usecases.verificationcontext.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.identity.IdentityMother;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest.LoadSequencesRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class LoadSequencesRequestTest {

    private final LoadSequencesRequestBuilder builder = LoadSequencesRequest.builder();

    @Test
    void shouldReturnChannel() {
        final Channel channel = mock(Channel.class);

        final LoadSequencesRequest request = builder.channel(channel).build();

        assertThat(request.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldReturnActivity() {
        final Activity activity = mock(Activity.class);

        final LoadSequencesRequest request = builder.activity(activity).build();

        assertThat(request.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldReturnProvidedAlias() {
        final Alias providedAlias = mock(Alias.class);

        final LoadSequencesRequest request = builder.providedAlias(providedAlias).build();

        assertThat(request.getProvidedAlias()).isEqualTo(providedAlias);
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

}
