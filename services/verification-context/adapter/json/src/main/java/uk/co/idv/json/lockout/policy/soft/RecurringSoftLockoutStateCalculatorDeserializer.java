package uk.co.idv.json.lockout.policy.soft;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class RecurringSoftLockoutStateCalculatorDeserializer extends StdDeserializer<RecurringSoftLockoutStateCalculator> {

    public RecurringSoftLockoutStateCalculatorDeserializer() {
        super(RecurringSoftLockoutStateCalculator.class);
    }

    @Override
    public RecurringSoftLockoutStateCalculator deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return new RecurringSoftLockoutStateCalculator(
                JsonNodeConverter.toObject(node.get("interval"), parser, SoftLockInterval.class)
        );
    }

}
