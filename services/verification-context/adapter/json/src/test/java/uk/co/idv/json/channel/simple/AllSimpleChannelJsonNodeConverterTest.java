package uk.co.idv.json.channel.simple;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.json.channel.ChannelJsonNodeConverter;
import uk.co.idv.json.channel.converter.AllSimpleChannelJsonNodeConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AllSimpleChannelJsonNodeConverterTest {

    private final ChannelJsonNodeConverter converter = new AllSimpleChannelJsonNodeConverter();

    @Test
    void shouldSupportAnyChannel() {
        assertThat(converter.supportsChannel("any")).isTrue();
    }

    @Test
    void shouldConvertAnyChannelJsonNodeToChannel() {
        final String id = "any";
        final JsonNode node = mock(JsonNode.class);
        given(node.get("id")).willReturn(TextNode.valueOf(id));

        final Channel channel = converter.toChannel(node);

        assertThat(channel.getId()).isEqualTo(id);
    }

}
