package uk.co.mruoc.idv.identity.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.mruoc.idv.identity.domain.model.Alias;

import java.io.IOException;

public class AliasSerializer extends StdSerializer<Alias> {

    protected AliasSerializer() {
        super(Alias.class);
    }

    @Override
    public void serialize(final Alias alias, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        toJson(alias, json);
    }

    public static void toJson(final Alias alias, final JsonGenerator json) throws IOException {
        json.writeStartObject();
        json.writeStringField("type", alias.getType());
        json.writeStringField("value", alias.getValue());
        json.writeEndObject();
    }

}
