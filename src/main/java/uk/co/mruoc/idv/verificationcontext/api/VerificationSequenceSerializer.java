package uk.co.mruoc.idv.verificationcontext.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequence;

import java.io.IOException;
import java.io.UncheckedIOException;

public class VerificationSequenceSerializer extends StdSerializer<VerificationSequence> {

    protected VerificationSequenceSerializer() {
        super(VerificationSequence.class);
    }

    @Override
    public void serialize(final VerificationSequence sequence, final JsonGenerator json, final SerializerProvider provider) {
        toJson(sequence, json, provider);
    }

    private void toJson(final VerificationSequence sequence, final JsonGenerator json, final SerializerProvider provider) {
        try {
            json.writeStartObject();
            json.writeNumberField("duration", sequence.getDuration().toMillis());
            json.writeBooleanField("eligible", sequence.isEligible());
            json.writeBooleanField("complete", sequence.isComplete());
            json.writeBooleanField("successful", sequence.isSuccessful());
            json.writeFieldName("methods");
            provider.defaultSerializeValue(sequence.getMethods(), json);
            if (sequence.hasResults()) {
                writeResults(sequence, json, provider);
            }
            json.writeEndObject();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void writeResults(final VerificationSequence sequence, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        json.writeFieldName("results");
        provider.defaultSerializeValue(sequence.getResults(), json);
    }

}
