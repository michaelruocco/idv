package uk.co.idv.json.activity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.activity.OnlinePurchase;
import uk.co.idv.domain.entities.activity.SimpleActivity;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class ActivityDeserializer extends StdDeserializer<Activity> {

    private final Map<String, Class<? extends Activity>> mappings;

    public ActivityDeserializer() {
        this(buildDefaultMappings());
    }

    public ActivityDeserializer(final Map<String, Class<? extends Activity>> mappings) {
        super(Activity.class);
        this.mappings = mappings;
    }

    @Override
    public Activity deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = JsonParserConverter.toNode(parser);
        final String name = ActivityJsonNodeConverter.extractName(node);
        final Optional<Class<? extends Activity>> type = toMappingType(name);
        return type.map(t -> toSpecificActivity(node, parser, t))
                .orElseGet(() -> toSimpleActivity(node));
    }

    private Optional<Class<? extends Activity>> toMappingType(final String name) {
        return Optional.ofNullable(mappings.get(name));
    }

    private static Activity toSpecificActivity(final JsonNode node, final JsonParser parser, final Class<? extends Activity> type) {
        return JsonNodeConverter.toObject(node, parser, type);
    }

    private static Activity toSimpleActivity(final JsonNode node) {
        return SimpleActivity.builder()
                .name(ActivityJsonNodeConverter.extractName(node))
                .timestamp(ActivityJsonNodeConverter.extractTimestamp(node))
                .build();
    }

    private static Map<String, Class<? extends Activity>> buildDefaultMappings() {
        return Map.of(OnlinePurchase.NAME, OnlinePurchase.class);
    }

}
