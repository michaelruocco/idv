package uk.co.mruoc.idv.json.identity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.idv.domain.entities.identity.alias.Alias;

import java.io.IOException;

public class AliasSerializer extends StdSerializer<Alias> {

    AliasSerializer() {
        super(Alias.class);
    }

    @Override
    public void serialize(final Alias alias, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        toJson(alias, json);
    }

    private void toJson(final Alias alias, final JsonGenerator json) throws IOException {
        json.writeStartObject();
        json.writeStringField("type", alias.getType());
        json.writeStringField("value", alias.getValue());
        json.writeEndObject();
    }

}
