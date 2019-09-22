package uk.co.mruoc.jsonapi.fake;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.mruoc.jsonapi.AbstractJsonApiDocumentDeserializer;

import java.io.IOException;

public class FakeJsonApiDocumentDeserializer extends AbstractJsonApiDocumentDeserializer<FakeJsonApiDocument> {

    protected FakeJsonApiDocumentDeserializer() {
        super(FakeJsonApiDocument.class);
    }

    protected FakeJsonApiDocument toDocument(final JsonParser parser, final JsonNode attributesNode, final String jsonApiType) throws IOException {
        final FakeAttributes attributes = attributesNode.traverse(parser.getCodec()).readValueAs(FakeAttributes.class);
        return new FakeJsonApiDocument(jsonApiType, attributes);
    }

}
