package uk.co.idv.api.verification.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.usecases.verification.onetimepasscode.CreateOneTimePasscodeVerificationRequest;
import uk.co.mruoc.jsonapi.ApiDataDocumentRequest;
import uk.co.mruoc.jsonapi.ApiDocumentDeserializer;
import uk.co.mruoc.jsonapi.ApiDocumentFactory;

import java.io.IOException;

public class CreateOneTimePasscodeVerificationRequestDocumentDeserializer extends ApiDocumentDeserializer<CreateOneTimePasscodeVerificationRequestDocument> {

    protected CreateOneTimePasscodeVerificationRequestDocumentDeserializer() {
        super(CreateOneTimePasscodeVerificationRequestDocument.class, new DocumentFactory());
    }

    private static class DocumentFactory implements ApiDocumentFactory<CreateOneTimePasscodeVerificationRequestDocument> {

        @Override
        public CreateOneTimePasscodeVerificationRequestDocument build(final ApiDataDocumentRequest request) throws IOException {
            final JsonNode dataNode = request.getDataNode();
            final JsonNode attributesNode = ApiDocumentFactory.extractAttributesNode(dataNode);
            final JsonParser parser = request.getParser();
            final CreateOneTimePasscodeVerificationRequest attributes = attributesNode.traverse(parser.getCodec()).readValueAs(CreateOneTimePasscodeVerificationRequest.class);
            return new CreateOneTimePasscodeVerificationRequestDocument(attributes);
        }

    }

}
