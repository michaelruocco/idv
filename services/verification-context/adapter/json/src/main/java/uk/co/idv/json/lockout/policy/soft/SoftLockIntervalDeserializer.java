package uk.co.idv.json.lockout.policy.soft;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;

import java.io.IOException;

public class SoftLockIntervalDeserializer extends StdDeserializer<SoftLockInterval> {

    public SoftLockIntervalDeserializer() {
        super(SoftLockInterval.class);
    }

    @Override
    public SoftLockInterval deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        return new SoftLockInterval(
                node.get("numberOfAttempts").asInt(),
                JsonNodeConverter.toDuration(node.get("duration"))
        );
    }

}
