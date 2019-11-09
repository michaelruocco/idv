package uk.co.mruoc.idv.api.lockout;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.mruoc.jsonapi.ApiDataDocumentRequest;
import uk.co.mruoc.jsonapi.ApiDocumentDeserializer;
import uk.co.mruoc.jsonapi.ApiDocumentFactory;

import java.io.IOException;

public class ResetLockoutStateDocumentDeserializer extends ApiDocumentDeserializer<ResetLockoutStateDocument> {

    protected ResetLockoutStateDocumentDeserializer() {
        super(ResetLockoutStateDocument.class, new DocumentFactory());
    }

    private static class DocumentFactory implements ApiDocumentFactory<ResetLockoutStateDocument> {

        @Override
        public ResetLockoutStateDocument build(final ApiDataDocumentRequest request) throws IOException {
            final JsonNode dataNode = request.getDataNode();
            final JsonNode attributesNode = ApiDocumentFactory.extractAttributesNode(dataNode);
            final JsonParser parser = request.getParser();
            final LockoutRequest attributes = attributesNode.traverse(parser.getCodec()).readValueAs(LockoutRequest.class);
            return new ResetLockoutStateDocument(attributes);
        }

    }

}
