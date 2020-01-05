package uk.co.idv.json.lockout.policy.soft;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockIntervals;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutStateCalculator;
import uk.co.idv.json.lockout.policy.LockoutPolicyJsonNodeConverter;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class SoftLockoutPolicyJsonNodeConverter implements LockoutPolicyJsonNodeConverter {

    @Override
    public boolean supportsType(String type) {
        return SoftLockoutStateCalculator.TYPE.equals(type);
    }

    @Override
    public LockoutPolicy toPolicy(final JsonNode node, final JsonParser parser, final DeserializationContext context) {
        try {
            return convert(node, parser);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static LockoutPolicy convert(final JsonNode node, final JsonParser parser) throws IOException {
        return new SoftLockoutPolicy(
                UUID.fromString(node.get("id").asText()),
                toLockoutLevel(parser, node.get("lockoutLevel")),
                toRecordAttemptStrategy(parser, node.get("recordAttemptStrategy")),
                toIntervals(parser, node.get("intervals"))
        );
    }

    private static LockoutLevel toLockoutLevel(final JsonParser parser, final JsonNode node) throws IOException {
        return node.traverse(parser.getCodec()).readValueAs(LockoutLevel.class);
    }

    private static RecordAttemptStrategy toRecordAttemptStrategy(final JsonParser parser, final JsonNode node) throws IOException {
        return node.traverse(parser.getCodec()).readValueAs(RecordAttemptStrategy.class);
    }

    private static SoftLockIntervals toIntervals(final JsonParser parser, final JsonNode node) throws IOException {
        final Collection<SoftLockInterval> intervals = new ArrayList<>();
        for (final JsonNode itemNode : node) {
            intervals.add(toInterval(parser, itemNode));
        }
        return new SoftLockIntervals(intervals);
    }

    private static SoftLockInterval toInterval(final JsonParser parser, final JsonNode node) throws IOException {
        return node.traverse(parser.getCodec()).readValueAs(SoftLockInterval.class);
    }

}
