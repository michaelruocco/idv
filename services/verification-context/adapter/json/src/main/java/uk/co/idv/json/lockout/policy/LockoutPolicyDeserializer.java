package uk.co.idv.json.lockout.policy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutPolicy;
import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class LockoutPolicyDeserializer extends StdDeserializer<LockoutPolicy> {

    public LockoutPolicyDeserializer() {
        super(LockoutPolicy.class);
    }

    @Override
    public LockoutPolicy deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return new DefaultLockoutPolicy(
                JsonNodeConverter.toUUID(node.get("id")),
                toLevel(node, parser),
                toStateCalculator(node, parser),
                toRecordAttempts(node, parser)
        );
    }

    public static LockoutStateCalculator toStateCalculator(final JsonNode node, final JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("stateCalculator"), parser, LockoutStateCalculator.class);
    }

    public static PolicyLevel toLevel(final JsonNode node, final JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("level"), parser, PolicyLevel.class);
    }

    public static RecordAttemptStrategy toRecordAttempts(final JsonNode node, final JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("recordAttempts"), parser, RecordAttemptStrategy.class);
    }

}
