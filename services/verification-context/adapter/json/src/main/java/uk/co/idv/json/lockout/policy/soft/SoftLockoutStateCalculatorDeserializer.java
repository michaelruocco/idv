package uk.co.idv.json.lockout.policy.soft;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockIntervals;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutStateCalculator;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class SoftLockoutStateCalculatorDeserializer extends StdDeserializer<SoftLockoutStateCalculator> {

    public SoftLockoutStateCalculatorDeserializer() {
        super(SoftLockoutStateCalculator.class);
    }

    @Override
    public SoftLockoutStateCalculator deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return new SoftLockoutStateCalculator(
                JsonNodeConverter.toObject(node.get("intervals"), parser, SoftLockIntervals.class)
        );
    }

}
