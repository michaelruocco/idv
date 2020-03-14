package uk.co.idv.api.verification.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.usecases.verification.onetimepasscode.SendOneTimePasscodeRequest;
import uk.co.mruoc.jsonapi.ApiDataDocumentRequest;
import uk.co.mruoc.jsonapi.ApiDocumentDeserializer;
import uk.co.mruoc.jsonapi.ApiDocumentFactory;

import java.io.IOException;

public class SendOneTimePasscodeDocumentDeserializer extends ApiDocumentDeserializer<SendOneTimePasscodeRequestDocument> {

    protected SendOneTimePasscodeDocumentDeserializer() {
        super(SendOneTimePasscodeRequestDocument.class, new DocumentFactory());
    }

    private static class DocumentFactory implements ApiDocumentFactory<SendOneTimePasscodeRequestDocument> {

        @Override
        public SendOneTimePasscodeRequestDocument build(final ApiDataDocumentRequest request) throws IOException {
            final JsonNode dataNode = request.getDataNode();
            final JsonNode attributesNode = ApiDocumentFactory.extractAttributesNode(dataNode);
            final JsonParser parser = request.getParser();
            final SendOneTimePasscodeRequest attributes = attributesNode.traverse(parser.getCodec()).readValueAs(SendOneTimePasscodeRequest.class);
            return new SendOneTimePasscodeRequestDocument(attributes);
        }

    }

}
