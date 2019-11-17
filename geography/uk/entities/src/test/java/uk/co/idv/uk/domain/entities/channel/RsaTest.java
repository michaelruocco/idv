package uk.co.idv.uk.domain.entities.channel;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.channel.Channel;


import static org.assertj.core.api.Assertions.assertThat;

class RsaTest {

    @Test
    void shouldReturnId() {
        final Channel channel = UkChannelMother.rsa();

        assertThat(channel.getId()).isEqualTo("RSA");
    }

    @Test
    void shouldReturnEmptyIssuerSessionIdIfProvidedNotProvided() {
        final Rsa channel = UkChannelMother.rsa();

        assertThat(channel.getIssuerSessionId()).isEmpty();
    }

    @Test
    void shouldReturnIssuerSessionIdIfProvided() {
        final String issuerSessionId = "1234567890";

        final Rsa channel = UkChannelMother.rsaBuilder()
                .issuerSessionId(issuerSessionId)
                .build();

        assertThat(channel.getIssuerSessionId()).contains(issuerSessionId);
    }

    @Test
    void shouldBeEqualIfAllValuesAreTheSame() {
        EqualsVerifier.forClass(Rsa.class)
                .withRedefinedSuperclass()
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
