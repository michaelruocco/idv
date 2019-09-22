package uk.co.mruoc.idv.verificationcontext.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSms;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotification;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

import java.io.IOException;

public class VerificationMethodSerializer extends StdSerializer<VerificationMethod> {

    VerificationMethodSerializer() {
        super(VerificationMethod.class);
    }

    @Override
    public void serialize(final VerificationMethod method, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        toJson(method, json, provider);
    }

    private void toJson(final VerificationMethod method, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        if (method instanceof PushNotification) {
            toJson((PushNotification) method, json);
        } else if (method instanceof MobilePinsentry) {
            toJson((MobilePinsentry) method, json);
        } else if (method instanceof PhysicalPinsentry) {
            toJson((PhysicalPinsentry) method, json, provider);
        } else if (method instanceof OneTimePasscodeSms) {
            toJson((OneTimePasscodeSms) method, json, provider);
        } else {
            toJson(method, json);
        }
    }

    private void toJson(final PushNotification method, final JsonGenerator json) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        json.writeEndObject();
    }

    private void toJson(final MobilePinsentry method, final JsonGenerator json) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        json.writeStringField("function", method.getFunction().name());
        json.writeEndObject();
    }

    private void toJson(final PhysicalPinsentry method, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        json.writeStringField("function", method.getFunction().name());
        json.writeFieldName("cardNumbers");
        provider.defaultSerializeValue(method.getCardNumbers(), json);
        json.writeEndObject();
    }

    private void toJson(final OneTimePasscodeSms method, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        json.writeFieldName("passcodeSettings");
        provider.defaultSerializeValue(method.getPasscodeSettings(), json);
        json.writeFieldName("mobileNumbers");
        provider.defaultSerializeValue(method.getMobileNumbers(), json);
        json.writeEndObject();
    }

    private void writeCommonFields(final VerificationMethod method, final JsonGenerator json) throws IOException {
        json.writeStringField("name", method.getName());
        json.writeNumberField("duration", method.getDuration().toMillis());
        json.writeBooleanField("eligible", method.isEligible());
    }

    private void toJson(final VerificationMethod method, final JsonGenerator json) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        json.writeEndObject();
    }

}
