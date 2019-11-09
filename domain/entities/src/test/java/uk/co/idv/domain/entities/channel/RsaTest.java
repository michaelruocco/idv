package uk.co.idv.domain.entities.channel;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RsaTest {

    private final Channel channel = new Rsa();

    @Test
    void shouldReturnId() {
        assertThat(channel.getId()).isEqualTo("RSA");
    }

    @Test
    void shouldBeEqualIfAllValuesAreTheSame() {
        EqualsVerifier.forClass(Rsa.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
