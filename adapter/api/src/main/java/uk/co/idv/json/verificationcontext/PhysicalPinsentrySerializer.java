package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryEligible;

import java.io.IOException;

public class PhysicalPinsentrySerializer extends StdSerializer<PhysicalPinsentry> {

    PhysicalPinsentrySerializer() {
        super(PhysicalPinsentry.class);
    }

    @Override
    public void serialize(final PhysicalPinsentry method,
                          final JsonGenerator json,
                          final SerializerProvider provider) throws IOException {
        if (method.isEligible()) {
            writeEligibleJson((PhysicalPinsentryEligible) method, json, provider);
            return;
        }
        writeIneligibleJson(method, json);
    }

    private void writeEligibleJson(final PhysicalPinsentryEligible method,
                                   final JsonGenerator json,
                                   final SerializerProvider provider) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        JsonFieldWriter.writeComplete(method.isComplete(), json);
        JsonFieldWriter.writeSuccessful(method.isSuccessful(), json);
        JsonFieldWriter.writeDuration(method.getDuration(), json);
        JsonFieldWriter.writeMaxAttempts(method.getMaxAttempts(), json);
        JsonFieldWriter.writeCardNumbersJson(method.getCardNumbers(), json, provider);
        if (method.hasResults()) {
            JsonFieldWriter.writeResults(method.getResults(), json, provider);
        }
        json.writeEndObject();
    }

    private void writeIneligibleJson(final PhysicalPinsentry method,
                                     final JsonGenerator json) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        json.writeEndObject();
    }

    private void writeCommonFields(final PhysicalPinsentry method,
                                   final JsonGenerator json) throws IOException {
        JsonFieldWriter.writeName(method.getName(), json);
        JsonFieldWriter.writeFunction(method.getFunction(), json);
        JsonFieldWriter.writeEligibility(method.getEligibility(), json);
    }

}
