package uk.co.idv.json.lockout.policy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;

import java.io.IOException;

public interface LockoutPolicyJsonNodeConverter {

    boolean supportsType(final String type);

    LockoutPolicy toPolicy(final JsonNode node, final JsonParser parser, final DeserializationContext context) throws IOException;

    static String toType(final JsonNode node) {
        return node.get("type").asText();
    }

    static LockoutLevel toLevel(final JsonNode node, final JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("level"), parser, LockoutLevel.class);
    }

    static RecordAttemptStrategy toRecordAttempts(final JsonNode node, final JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("recordAttempts"), parser, RecordAttemptStrategy.class);
    }

}
