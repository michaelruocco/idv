package uk.co.mruoc.idv.verificationcontext.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PasscodeSettings;

import java.io.IOException;

public class PasscodeSettingsSerializer extends JsonSerializer<PasscodeSettings> {

    @Override
    public void serialize(final PasscodeSettings method,
                          final JsonGenerator json,
                          final SerializerProvider provider) throws IOException {
        toJson(method, json);
    }

    private void toJson(final PasscodeSettings passcode,
                        final JsonGenerator json) throws IOException {
        json.writeStartObject();
        json.writeNumberField("length", passcode.getLength());
        json.writeNumberField("duration", passcode.getDuration().toMillis());
        json.writeNumberField("maxAttempts", passcode.getMaxAttempts());
        json.writeEndObject();
    }

}
