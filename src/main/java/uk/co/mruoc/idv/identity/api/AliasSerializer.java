package uk.co.mruoc.idv.identity.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.mruoc.idv.identity.domain.model.Alias;

import java.io.IOException;
import java.io.UncheckedIOException;

public class AliasSerializer extends StdSerializer<Alias> {

    protected AliasSerializer() {
        super(Alias.class);
    }

    @Override
    public void serialize(final Alias alias, final JsonGenerator json, final SerializerProvider provider) {
        toJson(alias, json);
    }

    public static void toJson(final Alias alias, final JsonGenerator json) {
        try {
            json.writeStartObject();
            json.writeStringField("type", alias.getType());
            json.writeStringField("value", alias.getValue());
            json.writeEndObject();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
