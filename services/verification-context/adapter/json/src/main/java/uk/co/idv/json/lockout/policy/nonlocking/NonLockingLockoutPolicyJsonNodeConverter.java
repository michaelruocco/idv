package uk.co.idv.json.lockout.policy.nonlocking;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.json.lockout.policy.LockoutPolicyJsonNodeConverter;

import java.io.IOException;
import java.util.UUID;

public class NonLockingLockoutPolicyJsonNodeConverter implements LockoutPolicyJsonNodeConverter {

    @Override
    public boolean supportsType(String type) {
        return NonLockingLockoutStateCalculator.TYPE.equals(type);
    }

    @Override
    public LockoutPolicy toPolicy(final JsonNode node,
                                  final JsonParser parser,
                                  final DeserializationContext context) throws IOException {
        return new NonLockingLockoutPolicy(
                UUID.fromString(node.get("id").asText()),
                toLockoutLevel(parser, node.get("lockoutLevel")),
                toRecordAttemptStrategy(parser, node.get("recordAttemptStrategy"))
        );
    }

    private static LockoutLevel toLockoutLevel(final JsonParser parser,
                                               final JsonNode node) throws IOException {
        return node.traverse(parser.getCodec()).readValueAs(LockoutLevel.class);
    }

    private static RecordAttemptStrategy toRecordAttemptStrategy(final JsonParser parser,
                                                                 final JsonNode node) throws IOException {
        return node.traverse(parser.getCodec()).readValueAs(RecordAttemptStrategy.class);
    }

}
