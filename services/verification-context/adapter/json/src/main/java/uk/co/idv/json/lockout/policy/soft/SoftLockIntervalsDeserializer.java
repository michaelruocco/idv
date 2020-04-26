package uk.co.idv.json.lockout.policy.soft;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockIntervals;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class SoftLockIntervalsDeserializer extends StdDeserializer<SoftLockIntervals> {

    public SoftLockIntervalsDeserializer() {
        super(SoftLockIntervals.class);
    }

    @Override
    public SoftLockIntervals deserialize(final JsonParser parser,final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        final SoftLockInterval[] numbers = JsonNodeConverter.toObject(node, parser, SoftLockInterval[].class);
        return new SoftLockIntervals(numbers);
    }

}
