package uk.co.mruoc.idv.identity.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.Aliases;

import java.io.IOException;

public class AliasesSerializer extends StdSerializer<Aliases> {

    AliasesSerializer() {
        super(Aliases.class);
    }

    @Override
    public void serialize(final Aliases aliases, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        toJson(aliases, json, provider);
    }

    private void toJson(final Aliases aliases, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        json.writeStartArray();
        for (final Alias alias : aliases) {
            provider.defaultSerializeValue(alias, json);
        }
        json.writeEndArray();
    }

}
