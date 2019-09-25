package uk.co.mruoc.idv.verificationcontext.api.result;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;

import java.io.IOException;

public class VerificationResultsSerializer extends StdSerializer<VerificationResults> {

    public VerificationResultsSerializer() {
        super(VerificationResults.class);
    }

    @Override
    public void serialize(final VerificationResults results,
                          final JsonGenerator json,
                          final SerializerProvider provider) throws IOException {
        json.writeStartArray();
        for (final VerificationResult result : results) {
            provider.defaultSerializeValue(result, json);
        }
        json.writeEndArray();
    }

}
