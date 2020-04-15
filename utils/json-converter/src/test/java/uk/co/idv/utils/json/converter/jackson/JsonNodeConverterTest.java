package uk.co.idv.utils.json.converter.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class JsonNodeConverterTest {

    @Test
    void shouldConvertJsonNodeToUuid() {
        final UUID expectedUuid = UUID.randomUUID();
        final JsonNode node = TextNode.valueOf(expectedUuid.toString());

        final UUID uuid = JsonNodeConverter.toUUID(node);

        assertThat(uuid).isEqualTo(expectedUuid);
    }

    @Test
    void shouldConvertJsonNodeToInstant() {
        final Instant expectedInstant = Instant.now();
        final JsonNode node = TextNode.valueOf(expectedInstant.toString());

        final Instant instant = JsonNodeConverter.toInstant(node);

        assertThat(instant).isEqualTo(expectedInstant);
    }

    @Test
    void shouldConvertJsonNodeToCollectionOfStrings() {
        final Collection<String> expectedValues = Arrays.asList("value1", "value2");
        final JsonNode node = toArrayNode(expectedValues);

        final Collection<String> values = JsonNodeConverter.toStringCollection(node, buildJsonParser());

        assertThat(values).containsExactlyElementsOf(expectedValues);
    }

    @Test
    void shouldThrowUncheckedIOExceptionIfJacksonThrowsIOException() throws IOException {
        final JsonParser parser = mock(JsonParser.class);
        final ObjectCodec codec = mock(ObjectCodec.class);
        given(parser.getCodec()).willReturn(codec);
        final JsonNode node = mock(JsonNode.class);
        given(node.traverse(codec)).willReturn(parser);
        final Class<Object> type = Object.class;
        given(parser.readValueAs(type)).willThrow(IOException.class);

        final Throwable error = catchThrowable(() -> JsonNodeConverter.toObject(node, parser, type));

        assertThat(error)
                .isInstanceOf(UncheckedIOException.class)
                .hasCauseInstanceOf(IOException.class);
    }

    private JsonNode toArrayNode(final Collection<String> values) {
        final ArrayNode node = new ArrayNode(JsonNodeFactory.withExactBigDecimals(false), values.size());
        values.forEach(node::add);
        return node;
    }

    private static JsonParser buildJsonParser() {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.getFactory().createParser("");
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
