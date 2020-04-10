package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeEligible;
import uk.co.idv.json.verificationcontext.method.JsonFieldWriter;

import java.io.IOException;

public class OneTimePasscodeSerializer extends StdSerializer<OneTimePasscode> {

    public OneTimePasscodeSerializer() {
        super(OneTimePasscode.class);
    }

    @Override
    public void serialize(final OneTimePasscode method,
                          final JsonGenerator json,
                          final SerializerProvider provider) throws IOException {
        if (method.isEligible()) {
            writeEligibleJson((OneTimePasscodeEligible) method, json, provider);
            return;
        }
        writeIneligibleJson(method, json);
    }

    private void writeEligibleJson(final OneTimePasscodeEligible method,
                                   final JsonGenerator json,
                                   final SerializerProvider provider) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        JsonFieldWriter.writeComplete(method.isComplete(), json);
        JsonFieldWriter.writeSuccessful(method.isSuccessful(), json);
        JsonFieldWriter.writeDuration(method.getDuration(), json);
        JsonFieldWriter.writeMaxAttempts(method.getMaxAttempts(), json);
        JsonFieldWriter.writePasscodeSettings(method.getPasscodeSettings(), json, provider);
        JsonFieldWriter.writeDeliveryMethods(method.getDeliveryMethods(), json, provider);
        JsonFieldWriter.writeResults(method.getResults(), json, provider);
        json.writeEndObject();
    }

    private void writeIneligibleJson(final OneTimePasscode method,
                                     final JsonGenerator json) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        json.writeEndObject();
    }

    private void writeCommonFields(final OneTimePasscode method,
                                   final JsonGenerator json) throws IOException {
        JsonFieldWriter.writeName(method.getName(), json);
        JsonFieldWriter.writeEligibility(method.getEligibility(), json);
    }

}
