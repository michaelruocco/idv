package uk.co.idv.repository.dynamo.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.repository.dynamo.json.JsonConverter.JsonConversionException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class JacksonJsonConverterTest {

    private final ObjectMapper mapper = mock(ObjectMapper.class);

    private final JsonConverter converter = new JacksonJsonConverter(mapper);

    @Test
    void shouldThrowExceptionIfObjectConversionFails() throws JsonProcessingException {
        final Object object = new Object();
        given(mapper.writeValueAsString(object)).willThrow(JsonProcessingException.class);

        final Throwable error = catchThrowable(() -> converter.toJson(object));

        assertThat(error)
                .isInstanceOf(JsonConversionException.class)
                .hasCauseInstanceOf(JsonProcessingException.class);
    }

    @Test
    void shouldThrowExceptionIfJsonStringConversionFails() throws IOException {
        final String json = "json";
        given(mapper.readValue(json, Object.class)).willThrow(IOException.class);

        final Throwable error = catchThrowable(() -> converter.toObject(json, Object.class));

        assertThat(error)
                .isInstanceOf(JsonConversionException.class)
                .hasCauseInstanceOf(IOException.class);
    }

    @Test
    void shouldConvertObjectToJsonString() throws JsonProcessingException {
        final Object object = new Object();
        final String expectedJson = "json";
        given(mapper.writeValueAsString(object)).willReturn(expectedJson);

        final String json = converter.toJson(object);

        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldConvertJsonStringToObject() throws IOException {
        final String json = "json";
        final Object expectedObject = new Object();
        given(mapper.readValue(json, Object.class)).willReturn(expectedObject);

        final Object object = converter.toObject(json, Object.class);

        assertThat(object).isEqualTo(expectedObject);
    }

}