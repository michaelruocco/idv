package uk.co.idv.domain.usecases.verificationcontext.expiry;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequences;
import uk.co.idv.domain.usecases.verificationcontext.expiry.CalculateExpiryRequest.CalculateExpiryRequestBuilder;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CalculateExpiryRequestTest {

    private final CalculateExpiryRequestBuilder builder = CalculateExpiryRequest.builder();

    @Test
    void shouldReturnCreated() {
        final Instant created = Instant.now();

        final CalculateExpiryRequest request = builder.created(created).build();

        assertThat(request.getCreated()).isEqualTo(created);
    }

    @Test
    void shouldReturnChannel() {
        final Channel channel = mock(Channel.class);

        final CalculateExpiryRequest request = builder.channel(channel).build();

        assertThat(request.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldReturnActivity() {
        final Activity activity = mock(Activity.class);

        final CalculateExpiryRequest request = builder.activity(activity).build();

        assertThat(request.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldReturnSequences() {
        final VerificationSequences sequences = mock(VerificationSequences.class);

        final CalculateExpiryRequest request = builder.sequences(sequences).build();

        assertThat(request.getSequences()).isEqualTo(sequences);
    }

}
