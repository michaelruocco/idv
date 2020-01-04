package uk.co.idv.json.activity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.usecases.exception.ActivityNotSupportedException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class ActivityDeserializer extends StdDeserializer<Activity> {

    private final Collection<ActivityJsonNodeConverter> converters;

    public ActivityDeserializer(final ActivityJsonNodeConverter... converters) {
        this(Arrays.asList(converters));
    }

    public ActivityDeserializer(final Collection<ActivityJsonNodeConverter> converters) {
        super(Channel.class);
        this.converters = converters;
    }

    @Override
    public Activity deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final String name = ActivityJsonNodeConverter.extractName(node);
        return findConverter(name)
                .map(converter -> converter.toActivity(node))
                .orElseThrow(() -> new ActivityNotSupportedException(name));
    }

    private Optional<ActivityJsonNodeConverter> findConverter(final String name) {
        return converters.stream().filter(converter -> converter.supportsActivity(name)).findFirst();
    }

    private static String extractName(final JsonNode node) {
        return node.get("name").asText();
    }

    /*private static Map<String, JsonNodeToActivityConverter> buildConverters() {
        return Map.of(
                OnlinePurchase.NAME, new JsonNodeToOnlinePurchaseConverter(),
                Login.NAME, new JsonNodeToSimpleActivityConverter()
        );
    }*/

}
