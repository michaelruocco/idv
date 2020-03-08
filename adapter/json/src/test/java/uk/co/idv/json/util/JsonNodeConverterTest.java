package uk.co.idv.json.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class JsonNodeConverterTest {

    @Test
    void shouldConvertToUUID() {
        final UUID expectedId = UUID.randomUUID();
        final JsonNode node = new TextNode(expectedId.toString());

        final UUID id = JsonNodeConverter.toUUID(node);

        assertThat(id).isEqualTo(expectedId);
    }

}
