package uk.co.idv.json.channel;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.channel.SimpleChannel;
import uk.co.idv.json.channel.simple.SingleSimpleChannelJsonNodeConverter;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.idv.domain.usecases.exception.ChannelNotSupportedException;
import uk.co.idv.domain.entities.channel.Channel;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ChannelDeserializerTest {

    private static final String CHANNEL_ID = "SIMPLE";
    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldDeserializeRsa() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("channel/simple-channel.json");

        final Channel channel = MAPPER.readValue(json, Channel.class);

        assertThat(channel).isEqualToComparingFieldByField(new SimpleChannel(CHANNEL_ID));
    }

    @Test
    void shouldThrowExceptionIfChannelNotSupported() {
        final String json = ContentLoader.loadContentFromClasspath("channel/not-supported-channel.json");

        final Throwable error = catchThrowable(() -> MAPPER.readValue(json, Channel.class));

        assertThat(error)
                .isInstanceOf(ChannelNotSupportedException.class)
                .hasMessage("not-supported-channel");
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        final ChannelJsonNodeConverter converter = new SingleSimpleChannelJsonNodeConverter(CHANNEL_ID);
        mapper.registerModule(new ChannelModule(new ChannelDeserializer(converter)));
        return mapper;
    }

}
