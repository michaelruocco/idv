package uk.co.idv.utils.json.converter.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class JsonParserConverterTest {

    @Test
    void shouldConvertParserToNode() throws IOException {
        final JsonParser parser = mock(JsonParser.class);
        final ObjectCodec codec = mock(ObjectCodec.class);
        given(parser.getCodec()).willReturn(codec);
        final JsonNode expectedNode = mock(JsonNode.class);
        given(codec.readTree(parser)).willReturn(expectedNode);

        final JsonNode node = JsonParserConverter.toNode(parser);

        assertThat(node).isEqualTo(expectedNode);
    }

    @Test
    void shouldThrowUncheckedIOExceptionIfJacksonThrowsIOException() throws IOException {
        final JsonParser parser = mock(JsonParser.class);
        final ObjectCodec codec = mock(ObjectCodec.class);
        given(parser.getCodec()).willReturn(codec);
        given(codec.readTree(parser)).willThrow(IOException.class);

        final Throwable error = catchThrowable(() -> JsonParserConverter.toNode(parser));

        assertThat(error)
                .isInstanceOf(UncheckedIOException.class)
                .hasCauseInstanceOf(IOException.class);
    }

}
