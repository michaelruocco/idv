package uk.co.mruoc.idv.domain.model.channel;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RsaChannelTest {

    private final Channel channel = new RsaChannel();

    @Test
    void shouldReturnId() {
        assertThat(channel.getId()).isEqualTo("RSA");
    }

}
