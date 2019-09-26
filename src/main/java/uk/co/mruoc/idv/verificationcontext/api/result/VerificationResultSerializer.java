package uk.co.mruoc.idv.verificationcontext.api.result;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import java.io.IOException;

public class VerificationResultSerializer extends StdSerializer<VerificationResult> {

    public VerificationResultSerializer() {
        super(VerificationResult.class);
    }

    @Override
    public void serialize(final VerificationResult result,
                          final JsonGenerator json,
                          final SerializerProvider provider) throws IOException {
        json.writeStartObject();
        json.writeStringField("methodName", result.getMethodName());
        json.writeStringField("verificationId", result.getVerificationId().toString());
        json.writeStringField("timestamp", result.getTimestamp().toString());
        json.writeBooleanField("successful", result.isSuccessful());
        json.writeEndObject();
    }

}
