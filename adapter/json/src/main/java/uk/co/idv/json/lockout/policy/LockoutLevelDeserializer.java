package uk.co.idv.json.lockout.policy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class LockoutLevelDeserializer extends StdDeserializer<LockoutLevel> {

    public LockoutLevelDeserializer() {
        super(LockoutLevel.class);
    }

    @Override
    public LockoutLevel deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        return DefaultLockoutLevel.builder()
                .channelId(node.get("channelId").asText())
                .activityNames(toStringCollection(node.get("activityNames")))
                .aliasTypes(toStringCollection(node.get("aliasTypes")))
                .build();
    }

    private Collection<String> toStringCollection(final JsonNode node) {
        final Collection<String> values = new ArrayList<>();
        for (final JsonNode itemNode : node) {
            values.add(itemNode.textValue());
        }
        return Collections.unmodifiableCollection(values);
    }

}
