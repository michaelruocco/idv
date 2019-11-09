package uk.co.mruoc.idv.api.verificationcontext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.usecases.verificationcontext.CreateContextRequest;
import uk.co.mruoc.jsonapi.ApiDataDocumentRequest;
import uk.co.mruoc.jsonapi.ApiDocumentDeserializer;
import uk.co.mruoc.jsonapi.ApiDocumentFactory;

import java.io.IOException;

public class CreateContextRequestDocumentDeserializer extends ApiDocumentDeserializer<CreateContextRequestDocument> {

    protected CreateContextRequestDocumentDeserializer() {
        super(CreateContextRequestDocument.class, new DocumentFactory());
    }

    private static class DocumentFactory implements ApiDocumentFactory<CreateContextRequestDocument> {

        @Override
        public CreateContextRequestDocument build(final ApiDataDocumentRequest request) throws IOException {
            final JsonNode dataNode = request.getDataNode();
            final JsonNode attributesNode = ApiDocumentFactory.extractAttributesNode(dataNode);
            final JsonParser parser = request.getParser();
            final CreateContextRequest attributes = attributesNode.traverse(parser.getCodec()).readValueAs(CreateContextRequest.class);
            return new CreateContextRequestDocument(attributes);
        }

    }

}
