package uk.co.idv.json.activity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.usecases.exception.ActivityNotSupportedException;

import java.io.IOException;
import java.io.UncheckedIOException;
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
                .map(converter -> toActivity(converter, node, parser, context))
                .orElseThrow(() -> new ActivityNotSupportedException(name));
    }

    private Optional<ActivityJsonNodeConverter> findConverter(final String name) {
        return converters.stream().filter(converter -> converter.supportsActivity(name)).findFirst();
    }

    private Activity toActivity(final ActivityJsonNodeConverter converter,
                                       final JsonNode node,
                                       final JsonParser parser,
                                       final DeserializationContext context) {
        try {
            return converter.toActivity(node, parser, context);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
