package uk.co.mruoc.idv.json.verificationcontext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;

import java.io.IOException;

public class VerificationMethodSerializer extends StdSerializer<VerificationMethod> {

    VerificationMethodSerializer() {
        super(VerificationMethod.class);
    }

    @Override
    public void serialize(final VerificationMethod method, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        if (method.isEligible()) {
            writeEligibleJson(method, json);
            return;
        }
        writeIneligibleJson(method, json);
    }

    private void writeEligibleJson(final VerificationMethod method, final JsonGenerator json) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        JsonFieldWriter.writeDuration(method.getDuration(), json);
        json.writeEndObject();
    }

    private void writeIneligibleJson(final VerificationMethod method, final JsonGenerator json) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        json.writeEndObject();
    }

    private void writeCommonFields(final VerificationMethod method, final JsonGenerator json) throws IOException {
        JsonFieldWriter.writeName(method.getName(), json);
        JsonFieldWriter.writeEligibility(method.getEligibility(), json);
    }

}
