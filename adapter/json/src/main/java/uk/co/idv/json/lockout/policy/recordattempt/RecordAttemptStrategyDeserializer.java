package uk.co.idv.json.lockout.policy.recordattempt;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;

import java.io.IOException;

public class RecordAttemptStrategyDeserializer extends StdDeserializer<RecordAttemptStrategy> {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory;

    public RecordAttemptStrategyDeserializer(final RecordAttemptStrategyFactory recordAttemptStrategyFactory) {
        super(RecordAttemptStrategy.class);
        this.recordAttemptStrategyFactory = recordAttemptStrategyFactory;
    }

    @Override
    public RecordAttemptStrategy deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        return recordAttemptStrategyFactory.build(node.get("type").asText());
    }

}
