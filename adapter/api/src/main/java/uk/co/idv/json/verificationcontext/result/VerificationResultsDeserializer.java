package uk.co.idv.json.verificationcontext.result;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultFailed;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

public class VerificationResultsDeserializer extends StdDeserializer<VerificationResults> {

    public VerificationResultsDeserializer() {
        super(VerificationResults.class);
    }

    @Override
    public VerificationResults deserialize(final JsonParser parser,final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        return new DefaultVerificationResults(node.traverse(parser.getCodec()).readValueAs(VerificationResult[].class));
    }

}
