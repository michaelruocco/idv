package uk.co.idv.api.verificationcontext.policy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.domain.entities.verificationcontext.policy.DefaultVerificationPolicy;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicy;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationSequencePolicy;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.mruoc.jsonapi.ApiDataDocumentRequest;
import uk.co.mruoc.jsonapi.ApiDocumentDeserializer;
import uk.co.mruoc.jsonapi.ApiDocumentFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class VerificationPolicyDocumentDeserializer extends ApiDocumentDeserializer<VerificationPolicyDocument> {

    public VerificationPolicyDocumentDeserializer() {
        super(VerificationPolicyDocument.class, new DocumentFactory());
    }

    private static class DocumentFactory implements ApiDocumentFactory<VerificationPolicyDocument> {

        @Override
        public VerificationPolicyDocument build(final ApiDataDocumentRequest request) {
            final VerificationPolicy attributes = toAttributes(request);
            return new VerificationPolicyDocument(attributes);
        }

        private VerificationPolicy toAttributes(final ApiDataDocumentRequest request) {
            final JsonNode dataNode = request.getDataNode();
            final JsonNode attributesNode = dataNode.get("attributes");
            return DefaultVerificationPolicy.builder()
                    .id(JsonNodeConverter.toUUID(dataNode.get("id")))
                    .level(toLevel(attributesNode, request.getParser()))
                    .sequencePolicies(toSequencePolicies(attributesNode, request.getParser()))
                    .build();
        }

        public static PolicyLevel toLevel(final JsonNode node, final JsonParser parser) {
            return JsonNodeConverter.toObject(node.get("level"), parser, PolicyLevel.class);
        }

        private static Collection<VerificationSequencePolicy> toSequencePolicies(final JsonNode node, final JsonParser parser) {
            return Optional.ofNullable(node.get("sequences"))
                    .map(results -> Arrays.asList(JsonNodeConverter.toObject(results, parser, VerificationSequencePolicy[].class)))
                    .orElseGet(Collections::emptyList);
        }

    }

}
