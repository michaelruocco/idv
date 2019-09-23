package uk.co.mruoc.jsonapi.error;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Map;

public class JsonApiErrorItemSerializer extends StdSerializer<JsonApiErrorItem> {

    public JsonApiErrorItemSerializer() {
        super(JsonApiErrorItem.class);
    }

    @Override
    public void serialize(final JsonApiErrorItem item, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        json.writeStartObject();
        json.writeStringField("id", item.getId().toString());
        json.writeStringField("status", Integer.toString(item.getStatus()));
        json.writeStringField("title", item.getTitle());
        json.writeStringField("detail", item.getDetail());
        final Map<String, Object> meta = item.getMeta();
        if (!meta.isEmpty()) {
            json.writeFieldName("meta");
            provider.defaultSerializeValue(meta, json);
        }
        json.writeEndObject();
    }

}
