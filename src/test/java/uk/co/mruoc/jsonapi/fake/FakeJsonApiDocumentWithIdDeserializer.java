package uk.co.mruoc.jsonapi.fake;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.mruoc.jsonapi.AbstractJsonApiDocumentWithIdDeserializer;

import java.io.IOException;
import java.util.UUID;

public class FakeJsonApiDocumentWithIdDeserializer extends AbstractJsonApiDocumentWithIdDeserializer<FakeJsonApiDocumentWithId> {

    FakeJsonApiDocumentWithIdDeserializer() {
        super(FakeJsonApiDocumentWithId.class);
    }

    protected FakeJsonApiDocumentWithId toDocument(final JsonParser parser,
                                             final JsonNode attributesNode,
                                             final String jsonApiType,
                                             final UUID id) throws IOException {
        final FakeAttributes attributes = attributesNode.traverse(parser.getCodec()).readValueAs(FakeAttributes.class);
        return new FakeJsonApiDocumentWithId(jsonApiType, attributes);
    }

}
