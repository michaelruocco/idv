package uk.co.mruoc.idv.identity.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.Aliases;

import java.io.IOException;
import java.io.UncheckedIOException;

public class AliasesSerializer extends JsonSerializer<Aliases> {

    @Override
    public void serialize(final Aliases aliases, final JsonGenerator json, final SerializerProvider provider) {
        toJson(aliases, json, provider);
    }

    private void toJson(final Aliases aliases, final JsonGenerator json, final SerializerProvider provider) {
        try {
            json.writeStartArray();
            for (final Alias alias : aliases) {
                provider.defaultSerializeValue(alias, json);
            }
            json.writeEndArray();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
