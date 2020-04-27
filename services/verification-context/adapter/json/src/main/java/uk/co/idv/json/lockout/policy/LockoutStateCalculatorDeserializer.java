package uk.co.idv.json.lockout.policy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.lockout.exception.LockoutTypeNotSupportedException;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

import java.util.Map;

public class LockoutStateCalculatorDeserializer extends StdDeserializer<LockoutStateCalculator> {

    private final Map<String, Class<? extends LockoutStateCalculator>> mappings;

    public LockoutStateCalculatorDeserializer() {
        this(buildDefaultMappings());
    }

    public LockoutStateCalculatorDeserializer(final Map<String, Class<? extends LockoutStateCalculator>> mappings) {
        super(LockoutStateCalculator.class);
        this.mappings = mappings;
    }

    @Override
    public LockoutStateCalculator deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        final String type = node.get("type").asText();
        if (!mappings.containsKey(type)) {
            throw new LockoutTypeNotSupportedException(type);
        }
        return JsonNodeConverter.toObject(node, parser, mappings.get(type));
    }

    private static Map<String, Class<? extends LockoutStateCalculator>> buildDefaultMappings() {
        return Map.of(
                SoftLockoutStateCalculator.TYPE, SoftLockoutStateCalculator.class,
                RecurringSoftLockoutStateCalculator.TYPE, RecurringSoftLockoutStateCalculator.class,
                HardLockoutStateCalculator.TYPE, HardLockoutStateCalculator.class,
                NonLockingStateCalculator.TYPE, NonLockingStateCalculator.class
        );
    }

}
