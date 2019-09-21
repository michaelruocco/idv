package uk.co.mruoc.idv.identity.domain.service;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.service.UpsertIdentityRequest.UpsertIdentityRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class UpsertIdentityRequestTest {

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

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(UpsertIdentityRequest.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
