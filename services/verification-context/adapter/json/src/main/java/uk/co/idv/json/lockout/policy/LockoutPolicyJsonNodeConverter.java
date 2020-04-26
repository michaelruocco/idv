package uk.co.idv.json.lockout.policy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;

public class LockoutPolicyJsonNodeConverter {

    private LockoutPolicyJsonNodeConverter() {
        // utility class
    }

    public static LockoutLevel toLevel(final JsonNode node, final JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("level"), parser, LockoutLevel.class);
    }

    public static RecordAttemptStrategy toRecordAttempts(final JsonNode node, final JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("recordAttempts"), parser, RecordAttemptStrategy.class);
    }

}
