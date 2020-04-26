package uk.co.idv.json.lockout.policy.nonlocking;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutStateCalculator;
import uk.co.idv.json.lockout.policy.LockoutPolicyJsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;

public class NonLockingLockoutPolicyJsonNodeConverter implements LockoutPolicyJsonNodeConverter {

    @Override
    public boolean supportsType(String type) {
        return NonLockingLockoutStateCalculator.TYPE.equals(type);
    }

    @Override
    public LockoutPolicy toPolicy(final JsonNode node,
                                  final JsonParser parser,
                                  final DeserializationContext context) {
        return new NonLockingLockoutPolicy(
                JsonNodeConverter.toUUID(node.get("id")),
                LockoutPolicyJsonNodeConverter.toLevel(node, parser),
                LockoutPolicyJsonNodeConverter.toRecordAttempts(node, parser)
        );
    }

}
