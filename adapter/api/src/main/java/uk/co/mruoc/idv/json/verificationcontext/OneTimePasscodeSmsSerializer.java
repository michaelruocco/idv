package uk.co.mruoc.idv.json.verificationcontext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.idv.domain.entities.verificationcontext.method.OneTimePasscodeSms;
import uk.co.idv.domain.entities.verificationcontext.method.OneTimePasscodeSmsEligible;

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
            writeEligibleJson((OneTimePasscodeSmsEligible) method, json, provider);
            return;
        }
        writeIneligibleJson(method, json);
    }

    private void writeEligibleJson(final OneTimePasscodeSmsEligible method,
                                   final JsonGenerator json,
                                   final SerializerProvider provider) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        JsonFieldWriter.writeComplete(method.isComplete(), json);
        JsonFieldWriter.writeSuccessful(method.isSuccessful(), json);
        JsonFieldWriter.writeDuration(method.getDuration(), json);
        JsonFieldWriter.writeMaxAttempts(method.getMaxAttempts(), json);
        JsonFieldWriter.writePasscodeSettings(method.getPasscodeSettings(), json, provider);
        JsonFieldWriter.writeMobileNumbersJson(method.getMobileNumbers(), json, provider);
        if (method.hasResults()) {
            JsonFieldWriter.writeResults(method.getResults(), json, provider);
        }
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
