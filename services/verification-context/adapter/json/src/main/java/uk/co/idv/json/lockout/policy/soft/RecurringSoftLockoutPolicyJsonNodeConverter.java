package uk.co.idv.json.lockout.policy.soft;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;
import uk.co.idv.json.lockout.policy.LockoutPolicyJsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;

public class RecurringSoftLockoutPolicyJsonNodeConverter implements LockoutPolicyJsonNodeConverter {

    @Override
    public boolean supportsType(String type) {
        return RecurringSoftLockoutStateCalculator.TYPE.equals(type);
    }

    @Override
    public LockoutPolicy toPolicy(final JsonNode node,
                                  final JsonParser parser,
                                  final DeserializationContext context) {
        return new RecurringSoftLockoutPolicy(
                JsonNodeConverter.toUUID(node.get("id")),
                LockoutPolicyJsonNodeConverter.toLevel(node, parser),
                LockoutPolicyJsonNodeConverter.toRecordAttempts(node, parser),
                JsonNodeConverter.toObject(node.get("interval"), parser, SoftLockInterval.class)
        );
    }

}
