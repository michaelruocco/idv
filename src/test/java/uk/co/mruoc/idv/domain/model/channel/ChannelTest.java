package uk.co.mruoc.idv.domain.model.channel;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChannelTest {

    private static final String ID = "fake-channel";

    private final Channel channel = new FakeChannel();

    @Test
    void shouldReturnId() {
        assertThat(channel.getId()).isEqualTo(ID);
    }

    @Test
    void shouldReturnHasId() {
        assertThat(channel.hasId(ID)).isTrue();
        assertThat(channel.hasId("other-id")).isFalse();
    }

    @Test
    void shouldBeEqualIfAllValuesAreTheSame() {
        EqualsVerifier.forClass(Rsa.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
