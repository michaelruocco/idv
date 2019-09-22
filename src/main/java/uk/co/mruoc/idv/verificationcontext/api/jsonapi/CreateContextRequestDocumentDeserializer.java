package uk.co.mruoc.idv.verificationcontext.api.jsonapi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.mruoc.idv.verificationcontext.domain.service.CreateContextRequest;
import uk.co.mruoc.jsonapi.AbstractJsonApiDocumentDeserializer;

import java.io.IOException;

public class CreateContextRequestDocumentDeserializer extends AbstractJsonApiDocumentDeserializer<CreateContextRequestDocument> {

    protected CreateContextRequestDocumentDeserializer() {
        super(CreateContextRequestDocument.class);
    }

    @Override
    protected CreateContextRequestDocument toDocument(final JsonParser parser, final JsonNode attributesNode, final String jsonApiType) throws IOException {
        final CreateContextRequest request = attributesNode.traverse(parser.getCodec()).readValueAs(CreateContextRequest.class);
        return new CreateContextRequestDocument(request);
    }

}
