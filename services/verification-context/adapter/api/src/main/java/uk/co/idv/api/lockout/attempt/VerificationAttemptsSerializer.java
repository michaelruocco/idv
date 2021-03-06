
package uk.co.idv.api.lockout.attempt;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempt;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

import java.io.IOException;

public class VerificationAttemptsSerializer extends StdSerializer<VerificationAttempts> {

    public VerificationAttemptsSerializer() {
        super(VerificationAttempts.class);
    }

    @Override
    public void serialize(final VerificationAttempts attempts, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        toJson(attempts, json, provider);
    }

    private void toJson(final VerificationAttempts attempts, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        json.writeStartArray();
        for (final VerificationAttempt attempt : attempts) {
            provider.defaultSerializeValue(attempt, json);
        }
        json.writeEndArray();
    }

}
