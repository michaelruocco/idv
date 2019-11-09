package uk.co.mruoc.idv.api.verificationcontext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;
import uk.co.mruoc.idv.verificationcontext.domain.service.RecordResultRequest;

import java.io.IOException;
import java.util.UUID;

public class UpdateContextResultsRequestDocumentDeserializer extends StdDeserializer<UpdateContextResultsRequestDocument> {

    public UpdateContextResultsRequestDocumentDeserializer() {
        super(UpdateContextResultsRequestDocument.class);
    }

    @Override
    public UpdateContextResultsRequestDocument deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final JsonNode dataNode = node.get("data");
        final RecordResultRequest request = RecordResultRequest.builder()
                .contextId(extractId(dataNode))
                .result(extractResult(parser, dataNode))
                .build();
        return new UpdateContextResultsRequestDocument(request);
    }

    private UUID extractId(final JsonNode dataNode) {
        return UUID.fromString(dataNode.get("id").asText());
    }

    private static VerificationResult extractResult(final JsonParser parser, final JsonNode dataNode) throws IOException {
        final JsonNode attributesNode = dataNode.get("attributes");
        final JsonNode resultNode = attributesNode.get("result");
        return toResult(parser, resultNode);
    }

    private static VerificationResult toResult(final JsonParser parser, final JsonNode node) throws IOException {
        return node.traverse(parser.getCodec()).readValueAs(VerificationResult.class);
    }

}
