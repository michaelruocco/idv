package uk.co.idv.json.lockout.policy.hard;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;
import uk.co.idv.json.lockout.policy.LockoutPolicyJsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;

public class HardLockoutPolicyJsonNodeConverter implements LockoutPolicyJsonNodeConverter {

    @Override
    public boolean supportsType(String type) {
        return HardLockoutStateCalculator.TYPE.equals(type);
    }

    @Override
    public LockoutPolicy toPolicy(final JsonNode node,
                                  final JsonParser parser,
                                  final DeserializationContext context) {
        return new HardLockoutPolicy(
                JsonNodeConverter.toUUID(node.get("id")),
                LockoutPolicyJsonNodeConverter.toLevel(node, parser),
                LockoutPolicyJsonNodeConverter.toRecordAttempts(node, parser),
                node.get("maxNumberOfAttempts").asInt()
        );
    }

}
