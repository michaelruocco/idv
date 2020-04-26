package uk.co.idv.json.lockout.policy.nonlocking;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutPolicy;
import uk.co.idv.json.lockout.policy.LockoutPolicyJsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class NonLockingLockoutPolicyDeserializer extends StdDeserializer<NonLockingLockoutPolicy> {

    public NonLockingLockoutPolicyDeserializer() {
        super(NonLockingLockoutPolicy.class);
    }

    @Override
    public NonLockingLockoutPolicy deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return new NonLockingLockoutPolicy(
                JsonNodeConverter.toUUID(node.get("id")),
                LockoutPolicyJsonNodeConverter.toLevel(node, parser),
                LockoutPolicyJsonNodeConverter.toRecordAttempts(node, parser)
        );
    }

}
