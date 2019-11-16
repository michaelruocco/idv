package uk.co.idv.uk.config.channel;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RsaTest {

    @Test
    void shouldReturnId() {
        final Rsa channel = new Rsa();

        assertThat(channel.getId()).isEqualTo("RSA");
    }

    @Test
    void shouldReturnEmptyIssuerSessionIdIfProvidedNotProvided() {
        final Rsa channel = new Rsa();

        assertThat(channel.getIssuerSessionId()).isEmpty();
    }

    @Test
    void shouldReturnIssuerSessionIdIfProvided() {
        final UUID issuerSessionId = UUID.randomUUID();

        final Rsa channel = new Rsa(issuerSessionId);

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
