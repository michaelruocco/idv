package uk.co.idv.api.lockout.policy;

import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.api.lockout.policy.hard.HardLockoutPolicyAttributes;
import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutStateCalculator;
import uk.co.mruoc.jsonapi.ApiDataDocumentRequest;
import uk.co.mruoc.jsonapi.ApiDocumentDeserializer;
import uk.co.mruoc.jsonapi.ApiDocumentFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class LockoutPolicyDocumentDeserializer extends ApiDocumentDeserializer<LockoutPolicyDocument> {

    public LockoutPolicyDocumentDeserializer() {
        super(LockoutPolicyDocument.class, new DocumentFactory());
    }

    private static class DocumentFactory implements ApiDocumentFactory<LockoutPolicyDocument> {

        @Override
        public LockoutPolicyDocument build(final ApiDataDocumentRequest request) {
            final LockoutPolicyAttributes attributes = toAttributes(request);
            return new LockoutPolicyDocument(attributes);
        }

        private LockoutPolicyAttributes toAttributes(final ApiDataDocumentRequest request) {
            final JsonNode dataNode = request.getDataNode();
            final JsonNode attributesNode = extractAttributes(dataNode);
            final String type = extractType(attributesNode);
            switch (type) {
                case HardLockoutStateCalculator.TYPE:
                    return toHardLockoutPolicyAttributes(dataNode);
                case NonLockingLockoutStateCalculator.TYPE:
                    return toLockoutPolicyAttributes(dataNode);
                default:
                    throw new LockoutTypeNotSupportedException(type);
            }
        }

        private HardLockoutPolicyAttributes toHardLockoutPolicyAttributes(final JsonNode dataNode) {
            final JsonNode attributesNode = extractAttributes(dataNode);
            return HardLockoutPolicyAttributes.builder()
                    .id(extractId(dataNode))
                    .lockoutLevel(extractLockoutLevel(attributesNode))
                    .recordAttempts(extractRecordAttemptStrategy(attributesNode))
                    .maxNumberOfAttempts(extractMaxNumberOfAttempts(attributesNode))
                    .build();
        }

        private static LockoutPolicyAttributes toLockoutPolicyAttributes(final JsonNode dataNode) {
            final JsonNode attributesNode = dataNode.get("attributes");
            return new DefaultLockoutPolicyAttributes(
                    extractId(dataNode),
                    extractType(attributesNode),
                    extractRecordAttemptStrategy(attributesNode),
                    extractLockoutLevel(attributesNode)
            );
        }

        private static String extractType(final JsonNode node) {
            return node.get("type").asText();
        }

        private static UUID extractId(final JsonNode node) {
            return UUID.fromString(node.get("id").asText());
        }

        private static LockoutLevel extractLockoutLevel(final JsonNode node) {
            final JsonNode levelNode = node.get("level");
            return DefaultLockoutLevel.builder()
                    .channelId(levelNode.get("channelId").asText())
                    .activityNames(toStringCollection(levelNode.get("activityNames")))
                    .aliasTypes(toStringCollection(levelNode.get("aliasTypes")))
                    .build();
        }

        private static Collection<String> toStringCollection(final JsonNode node) {
            final Collection<String> values = new ArrayList<>();
            for (final JsonNode value : node) {
                values.add(value.asText());
            }
            return Collections.unmodifiableCollection(values);
        }

        private static JsonNode extractAttributes(final JsonNode node) {
            return node.get("attributes");
        }

        private static String extractRecordAttemptStrategy(final JsonNode node) {
            return node.get("recordAttempts").asText();
        }

        private static int extractMaxNumberOfAttempts(final JsonNode node) {
            return node.get("maxNumberOfAttempts").asInt();
        }

        public static class LockoutTypeNotSupportedException extends RuntimeException {

            public LockoutTypeNotSupportedException(final String type) {
                super(type);
            }

        }

    }

}
