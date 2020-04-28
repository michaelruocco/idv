package uk.co.idv.api.lockout.policy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutPolicy;
import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.mruoc.jsonapi.ApiDataDocumentRequest;
import uk.co.mruoc.jsonapi.ApiDocumentDeserializer;
import uk.co.mruoc.jsonapi.ApiDocumentFactory;

public class LockoutPolicyDocumentDeserializer extends ApiDocumentDeserializer<LockoutPolicyDocument> {

    public LockoutPolicyDocumentDeserializer() {
        super(LockoutPolicyDocument.class, new DocumentFactory());
    }

    private static class DocumentFactory implements ApiDocumentFactory<LockoutPolicyDocument> {

        @Override
        public LockoutPolicyDocument build(final ApiDataDocumentRequest request) {
            final LockoutPolicy attributes = toAttributes(request);
            return new LockoutPolicyDocument(attributes);
        }

        private LockoutPolicy toAttributes(final ApiDataDocumentRequest request) {
            final JsonNode dataNode = request.getDataNode();
            final JsonNode attributesNode = dataNode.get("attributes");
            return new DefaultLockoutPolicy(
                    JsonNodeConverter.toUUID(dataNode.get("id")),
                    toStateCalculator(attributesNode, request.getParser()),
                    toLevel(attributesNode, request.getParser()),
                    toRecordAttempts(attributesNode, request.getParser())
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

}
