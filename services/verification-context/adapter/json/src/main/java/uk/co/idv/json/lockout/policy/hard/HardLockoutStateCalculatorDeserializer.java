package uk.co.idv.json.lockout.policy.hard;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class HardLockoutStateCalculatorDeserializer extends StdDeserializer<HardLockoutStateCalculator> {

    public HardLockoutStateCalculatorDeserializer() {
        super(HardLockoutStateCalculator.class);
    }

    @Override
    public HardLockoutStateCalculator deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return new HardLockoutStateCalculator(
                node.get("maxNumberOfAttempts").asInt()
        );
    }

}
