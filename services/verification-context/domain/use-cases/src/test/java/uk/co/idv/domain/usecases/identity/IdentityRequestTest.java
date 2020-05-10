package uk.co.idv.domain.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequest.UpsertIdentityRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class IdentityRequestTest {

    private final UpsertIdentityRequestBuilder builder = UpsertIdentityRequest.builder();

    @Test
    void shouldReturnChannel() {
        final Channel channel = mock(Channel.class);

        final UpsertIdentityRequest request = builder.channel(channel).build();

        assertThat(request.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldReturnProvidedAlias() {
        final Alias providedAlias = mock(Alias.class);

        final UpsertIdentityRequest request = builder.providedAlias(providedAlias).build();

        assertThat(request.getProvidedAlias()).isEqualTo(providedAlias);
    }

}
