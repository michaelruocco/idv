package uk.co.mruoc.idv.verificationcontext.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentry;

import java.io.IOException;

public class MobilePinsentrySerializer extends StdSerializer<MobilePinsentry> {

    MobilePinsentrySerializer() {
        super(MobilePinsentry.class);
    }

    @Override
    public void serialize(final MobilePinsentry method,
                          final JsonGenerator json,
                          final SerializerProvider provider) throws IOException {
        if (method.isEligible()) {
            writeEligibleJson(method, json, provider);
            return;
        }
        writeIneligibleJson(method, json);
    }

    private void writeEligibleJson(final MobilePinsentry method,
                                   final JsonGenerator json,
                                   final SerializerProvider provider) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        JsonFieldWriter.writeComplete(method.isComplete(), json);
        JsonFieldWriter.writeSuccessful(method.isSuccessful(), json);
        JsonFieldWriter.writeDuration(method.getDuration(), json);
        JsonFieldWriter.writeMaxAttempts(method.getMaxAttempts(), json);
        if (method.hasResults()) {
            JsonFieldWriter.writeResults(method.getResults(), json, provider);
        }
        json.writeEndObject();
    }

    private void writeIneligibleJson(final MobilePinsentry method,
                                     final JsonGenerator json) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        json.writeEndObject();
    }

    private void writeCommonFields(final MobilePinsentry method,
                                   final JsonGenerator json) throws IOException {
        JsonFieldWriter.writeName(method.getName(), json);
        JsonFieldWriter.writeFunction(method.getFunction(), json);
        JsonFieldWriter.writeEligibility(method.getEligibility(), json);
    }

}
