package uk.co.idv.uk.api.channel;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.uk.domain.entities.channel.rsa.Rsa;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class UkChannelModuleTest {

    private static final ObjectMapper MAPPER = UkObjectMapperSingleton.get();

    @Test
    void shouldSerializeRsaChannelWithIssuerSessionId() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("rsa/rsa-channel-with-issuer-session-id.json");

        final Channel channel = MAPPER.readValue(json, Channel.class);

        assertThat(channel).isEqualToComparingFieldByField(new Rsa("1234567890"));
    }

    @Test
    void shouldSerializeRsaChannelWithoutIssuerSessionId() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("rsa/rsa-channel-without-issuer-session-id.json");

        final Channel channel = MAPPER.readValue(json, Channel.class);

        assertThat(channel).isEqualToComparingFieldByField(new Rsa());
    }

}
