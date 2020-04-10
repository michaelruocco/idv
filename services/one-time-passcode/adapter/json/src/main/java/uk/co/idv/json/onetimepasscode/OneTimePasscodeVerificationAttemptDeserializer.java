package uk.co.idv.json.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerificationAttempt;

import java.io.IOException;
import java.time.Instant;

public class OneTimePasscodeVerificationAttemptDeserializer extends StdDeserializer<OneTimePasscodeVerificationAttempt> {

    OneTimePasscodeVerificationAttemptDeserializer() {
        super(OneTimePasscodeVerificationAttempt.class);
    }

    @Override
    public OneTimePasscodeVerificationAttempt deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        return OneTimePasscodeVerificationAttempt.builder()
                .passcode(node.get("passcode").asText())
                .created(Instant.parse(node.get("created").asText()))
                .build();
    }

}
