package uk.co.idv.api.lockout.policy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.api.lockout.policy.hard.HardLockoutPolicyAttributes;
import uk.co.idv.api.lockout.policy.soft.RecurringSoftLockoutPolicyAttributes;
import uk.co.idv.api.lockout.policy.soft.SoftLockoutPolicyAttributes;
import uk.co.idv.domain.entities.lockout.exception.LockoutTypeNotSupportedException;
import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockIntervals;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutStateCalculator;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.mruoc.jsonapi.ApiDataDocumentRequest;
import uk.co.mruoc.jsonapi.ApiDocumentDeserializer;
import uk.co.mruoc.jsonapi.ApiDocumentFactory;

import java.util.ArrayList;
import java.util.Collection;
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
            final String type = extractType(extractAttributes(dataNode));
            switch (type) {
                case HardLockoutStateCalculator.TYPE:
                    return toHardLockoutPolicyAttributes(dataNode);
                case NonLockingLockoutStateCalculator.TYPE:
                    return toLockoutPolicyAttributes(dataNode);
                case SoftLockoutStateCalculator.TYPE:
                    return toSoftLockoutPolicyAttributes(request);
                case RecurringSoftLockoutStateCalculator.TYPE:
                    return toRecurringSoftLockoutPolicyAttributes(request);
                default:
                    throw new LockoutTypeNotSupportedException(type);
            }
        }

        private HardLockoutPolicyAttributes toHardLockoutPolicyAttributes(final JsonNode dataNode) {
            final JsonNode attributesNode = extractAttributes(dataNode);
            return HardLockoutPolicyAttributes.builder()
                    .id(toId(dataNode))
                    .lockoutLevel(toLockoutLevel(attributesNode))
                    .recordAttempts(toRecordAttemptStrategy(attributesNode))
                    .maxNumberOfAttempts(extractMaxNumberOfAttempts(attributesNode))
                    .build();
        }

        private static LockoutPolicyAttributes toLockoutPolicyAttributes(final JsonNode dataNode) {
            final JsonNode attributesNode = extractAttributes(dataNode);
            return new DefaultLockoutPolicyAttributes(
                    toId(dataNode),
                    extractType(attributesNode),
                    toRecordAttemptStrategy(attributesNode),
                    toLockoutLevel(attributesNode)
            );
        }

        private static LockoutPolicyAttributes toSoftLockoutPolicyAttributes(final ApiDataDocumentRequest request) {
            final JsonNode dataNode = request.getDataNode();
            final JsonNode attributesNode = extractAttributes(dataNode);
            return SoftLockoutPolicyAttributes.builder()
                    .id(toId(dataNode))
                    .lockoutLevel(toLockoutLevel(attributesNode))
                    .recordAttempts(toRecordAttemptStrategy(attributesNode))
                    .intervals(toIntervals(attributesNode.get("intervals"), request.getParser()))
                    .build();
        }

        private static LockoutPolicyAttributes toRecurringSoftLockoutPolicyAttributes(final ApiDataDocumentRequest request) {
            final JsonNode dataNode = request.getDataNode();
            final JsonNode attributesNode = extractAttributes(dataNode);
            return RecurringSoftLockoutPolicyAttributes.builder()
                    .id(toId(dataNode))
                    .lockoutLevel(toLockoutLevel(attributesNode))
                    .recordAttempts(toRecordAttemptStrategy(attributesNode))
                    .interval(toInterval(attributesNode.get("interval"), request.getParser()))
                    .build();
        }

        private static String extractType(final JsonNode node) {
            return node.get("type").asText();
        }

        private static UUID toId(final JsonNode node) {
            return UUID.fromString(node.get("id").asText());
        }

        private static LockoutLevel toLockoutLevel(final JsonNode node) {
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
            return values;
        }

        private static SoftLockIntervals toIntervals(final JsonNode node, final JsonParser parser) {
            return JsonNodeConverter.toObject(node, parser, SoftLockIntervals.class);
        }

        private static SoftLockInterval toInterval(final JsonNode node, final JsonParser parser) {
            return JsonNodeConverter.toObject(node, parser, SoftLockInterval.class);
        }

        private static JsonNode extractAttributes(final JsonNode node) {
            return node.get("attributes");
        }

        private static String toRecordAttemptStrategy(final JsonNode node) {
            return node.get("recordAttempts").asText();
        }

        private static int extractMaxNumberOfAttempts(final JsonNode node) {
            return node.get("maxNumberOfAttempts").asInt();
        }

    }

}
