package uk.co.mruoc.idv.verificationcontext.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentry;

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
            writeEligibleJson(method, json, provider);
            return;
        }
        writeIneligibleJson(method, json);
    }

    private void writeEligibleJson(final PhysicalPinsentry method,
                                   final JsonGenerator json,
                                   final SerializerProvider provider) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        JsonFieldWriter.writeDuration(method.getDuration(), json);
        JsonFieldWriter.writeCardNumbersJson(method.getCardNumbers(), json, provider);
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
