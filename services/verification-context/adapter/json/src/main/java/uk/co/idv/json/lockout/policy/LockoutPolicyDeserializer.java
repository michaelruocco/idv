package uk.co.idv.json.lockout.policy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutStateCalculator;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

import java.util.Map;

public class LockoutPolicyDeserializer extends StdDeserializer<LockoutPolicy> {

    private final Map<String, Class<? extends LockoutPolicy>> mappings;

    public LockoutPolicyDeserializer() {
        this(buildDefaultMappings());
    }

    public LockoutPolicyDeserializer(final Map<String, Class<? extends LockoutPolicy>> mappings) {
        super(LockoutPolicy.class);
        this.mappings = mappings;
    }

    @Override
    public LockoutPolicy deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        final String type = node.get("type").asText();
        return JsonNodeConverter.toObject(node, parser, mappings.get(type));
    }

    private static Map<String, Class<? extends LockoutPolicy>> buildDefaultMappings() {
        return Map.of(
                SoftLockoutStateCalculator.TYPE, SoftLockoutPolicy.class,
                RecurringSoftLockoutStateCalculator.TYPE, RecurringSoftLockoutPolicy.class,
                HardLockoutStateCalculator.TYPE, HardLockoutPolicy.class,
                NonLockingLockoutStateCalculator.TYPE, NonLockingLockoutPolicy.class
        );
    }

}
