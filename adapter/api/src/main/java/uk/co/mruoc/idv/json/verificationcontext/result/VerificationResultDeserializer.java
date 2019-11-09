package uk.co.mruoc.idv.json.verificationcontext.result;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultFailed;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultSuccessful;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

public class VerificationResultDeserializer extends StdDeserializer<VerificationResult> {

    public VerificationResultDeserializer() {
        super(VerificationResult.class);
    }

    @Override
    public VerificationResult deserialize(final JsonParser parser,final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final boolean successful = node.get("successful").asBoolean();
        if (successful) {
            return toSuccessfulResult(node);
        }
        return toFailedResult(node);
    }

    private VerificationResult toSuccessfulResult(final JsonNode node) {
        return VerificationResultSuccessful.builder()
                .methodName(extractMethodName(node))
                .verificationId(extractVerificationId(node))
                .timestamp(extractTimetamp(node))
                .build();
    }

    private VerificationResult toFailedResult(final JsonNode node) {
        return VerificationResultFailed.builder()
                .methodName(extractMethodName(node))
                .verificationId(extractVerificationId(node))
                .timestamp(extractTimetamp(node))
                .build();
    }

    private static String extractMethodName(final JsonNode node) {
        return node.get("methodName").asText();
    }

    private static UUID extractVerificationId(final JsonNode node) {
        return UUID.fromString(node.get("verificationId").asText());
    }

    private static Instant extractTimetamp(final JsonNode node) {
        return Instant.parse(node.get("timestamp").asText());
    }

}
