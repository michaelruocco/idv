package uk.co.mruoc.jsonapi;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public abstract class AbstractJsonApiDocumentSerializer<T extends JsonApiDocument> extends StdSerializer<T> {

    protected AbstractJsonApiDocumentSerializer(final Class<T> type) {
        super(type);
    }

    @Override
    public void serialize(final T document, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        json.writeStartObject();
        json.writeFieldName("data");
        json.writeStartObject();
        json.writeStringField("type", document.getType());
        json.writeFieldName("attributes");
        writeAttributes(document, json, provider);
        json.writeEndObject();
        json.writeEndObject();
    }

    protected abstract void writeAttributes(T document, final JsonGenerator json, final SerializerProvider provider) throws IOException;

}
