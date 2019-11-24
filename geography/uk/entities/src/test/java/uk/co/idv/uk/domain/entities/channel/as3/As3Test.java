package uk.co.idv.uk.domain.entities.channel.as3;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.uk.domain.entities.channel.UkChannelMother;

import static org.assertj.core.api.Assertions.assertThat;

class As3Test {

    @Test
    void shouldReturnId() {
        final Channel channel = UkChannelMother.as3();

        assertThat(channel.getId()).isEqualTo("AS3");
    }

    @Test
    void shouldBeEqualIfAllValuesAreTheSame() {
        EqualsVerifier.forClass(As3.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
