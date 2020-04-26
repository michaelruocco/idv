package uk.co.idv.json.lockout.policy.soft;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;
import uk.co.idv.json.lockout.policy.LockoutPolicyJsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class RecurringSoftLockoutPolicyDeserializer extends StdDeserializer<RecurringSoftLockoutPolicy> {

    public RecurringSoftLockoutPolicyDeserializer() {
        super(RecurringSoftLockoutPolicy.class);
    }

    @Override
    public RecurringSoftLockoutPolicy deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return new RecurringSoftLockoutPolicy(
                JsonNodeConverter.toUUID(node.get("id")),
                LockoutPolicyJsonNodeConverter.toLevel(node, parser),
                LockoutPolicyJsonNodeConverter.toRecordAttempts(node, parser),
                JsonNodeConverter.toObject(node.get("interval"), parser, SoftLockInterval.class)
        );
    }

}
