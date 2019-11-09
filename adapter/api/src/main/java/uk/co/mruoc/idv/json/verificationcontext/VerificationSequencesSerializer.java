package uk.co.mruoc.idv.json.verificationcontext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequence;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences;

import java.io.IOException;

public class VerificationSequencesSerializer extends StdSerializer<VerificationSequences> {

    VerificationSequencesSerializer() {
        super(VerificationSequences.class);
    }

    @Override
    public void serialize(final VerificationSequences sequences, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        toJson(sequences, json, provider);
    }

    private void toJson(final VerificationSequences sequences, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        json.writeStartArray();
        for (final VerificationSequence sequence : sequences) {
            provider.defaultSerializeValue(sequence, json);
        }
        json.writeEndArray();
    }

}
