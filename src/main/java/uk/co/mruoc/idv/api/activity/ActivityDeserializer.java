package uk.co.mruoc.idv.api.activity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.model.activity.OnlinePurchase;

import java.io.IOException;

import static uk.co.mruoc.idv.api.activity.JsonNodeToOnlinePurchaseConverter.toOnlinePurchase;

public class ActivityDeserializer extends StdDeserializer<Activity> {

    public ActivityDeserializer() {
        super(Activity.class);
    }

    @Override
    public Activity deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final String name = extractName(node);
        if (OnlinePurchase.NAME.equals(name)) {
            return toOnlinePurchase(node);
        }
        throw new ActivityNotSupportedException(name);
    }

    private static String extractName(final JsonNode node) {
        return node.get("name").asText();
    }

    public static class ActivityNotSupportedException extends RuntimeException {

        private ActivityNotSupportedException(final String name) {
            super(name);
        }

    }

}
