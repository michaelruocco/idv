package uk.co.mruoc.jsonapi.fake;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import uk.co.mruoc.jsonapi.AbstractJsonApiDocumentSerializer;

import java.io.IOException;

public class FakeJsonApiDocumentSerializer extends AbstractJsonApiDocumentSerializer<FakeJsonApiDocument> {

    FakeJsonApiDocumentSerializer() {
        super(FakeJsonApiDocument.class);
    }

    @Override
    protected void writeAttributes(final FakeJsonApiDocument document, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        final FakeAttributes attributes = document.getAttributes();
        json.writeStartObject();
        json.writeStringField("value1", attributes.getValue1());
        json.writeStringField("value2", attributes.getValue2());
        json.writeEndObject();
    }

}
