package uk.co.idv.json.lockout.policy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;

import java.io.IOException;

public interface LockoutPolicyJsonNodeConverter {

    boolean supportsType(final String type);

    LockoutPolicy toPolicy(final JsonNode node, final JsonParser parser, final DeserializationContext context) throws IOException;

    static String extractLockoutType(final JsonNode node) {
        return node.get("lockoutType").asText();
    }

}
