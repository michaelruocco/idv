package uk.co.mruoc.idv.verificationcontext.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequence;

import java.io.IOException;

public class VerificationSequenceSerializer extends StdSerializer<VerificationSequence> {

    VerificationSequenceSerializer() {
        super(VerificationSequence.class);
    }

    @Override
    public void serialize(final VerificationSequence sequence, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        toJson(sequence, json, provider);
    }

    private void toJson(final VerificationSequence sequence, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        json.writeStartObject();
        json.writeStringField("name", sequence.getName());
        final boolean eligible = sequence.isEligible();
        json.writeBooleanField("eligible", eligible);
        if (eligible) {
            json.writeBooleanField("complete", sequence.isComplete());
            json.writeBooleanField("successful", sequence.isSuccessful());
            json.writeNumberField("duration", sequence.getDuration().toMillis());
        }
        json.writeFieldName("methods");
        provider.defaultSerializeValue(sequence.getMethods(), json);
        //if (sequence.hasResults()) {
        //    writeResults(sequence, json, provider);
        //}
        json.writeEndObject();
    }

    //private void writeResults(final VerificationSequence sequence, final JsonGenerator json, final SerializerProvider provider) throws IOException {
    //    json.writeFieldName("results");
    //    provider.defaultSerializeValue(sequence.getResults(), json);
    //}

}
