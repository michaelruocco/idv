package uk.co.mruoc.idv.verificationcontext.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSms;

import java.io.IOException;

public class OneTimePasscodeSmsSerializer extends StdSerializer<OneTimePasscodeSms> {

    OneTimePasscodeSmsSerializer() {
        super(OneTimePasscodeSms.class);
    }

    @Override
    public void serialize(final OneTimePasscodeSms method,
                          final JsonGenerator json,
                          final SerializerProvider provider) throws IOException {
        if (method.isEligible()) {
            writeEligibleJson(method, json, provider);
            return;
        }
        writeIneligibleJson(method, json);
    }

    private void writeEligibleJson(final OneTimePasscodeSms method,
                                   final JsonGenerator json,
                                   final SerializerProvider provider) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        JsonFieldWriter.writeDuration(method.getDuration(), json);
        JsonFieldWriter.writePasscodeSettings(method.getPasscodeSettings(), json, provider);
        JsonFieldWriter.writeMobileNumbersJson(method.getMobileNumbers(), json, provider);
        json.writeEndObject();
    }

    private void writeIneligibleJson(final OneTimePasscodeSms method,
                                     final JsonGenerator json) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        json.writeEndObject();
    }

    private void writeCommonFields(final OneTimePasscodeSms method,
                                   final JsonGenerator json) throws IOException {
        JsonFieldWriter.writeName(method.getName(), json);
        JsonFieldWriter.writeEligibility(method.getEligibility(), json);
    }

}