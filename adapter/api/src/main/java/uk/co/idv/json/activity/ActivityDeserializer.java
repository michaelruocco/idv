package uk.co.idv.json.activity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.activity.Login;
import uk.co.idv.domain.usecases.exception.ActivityNotSupportedException;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.activity.OnlinePurchase;

import java.io.IOException;
import java.util.Map;

public class ActivityDeserializer extends StdDeserializer<Activity> {

    private final Map<String, JsonNodeToActivityConverter> converters = buildConverters();

    public ActivityDeserializer() {
        super(Activity.class);
    }

    @Override
    public Activity deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final String name = extractName(node);
        if (converters.containsKey(name)) {
            final JsonNodeToActivityConverter converter = converters.get(name);
            return converter.toActivity(node);
        }
        throw new ActivityNotSupportedException(name);
    }

    private static String extractName(final JsonNode node) {
        return node.get("name").asText();
    }

    private static Map<String, JsonNodeToActivityConverter> buildConverters() {
        return Map.of(
                OnlinePurchase.NAME, new JsonNodeToOnlinePurchaseConverter(),
                Login.NAME, new JsonNodeToSimpleActivityConverter()
        );
    }

}
