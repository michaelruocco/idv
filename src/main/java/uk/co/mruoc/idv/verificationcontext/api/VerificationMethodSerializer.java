package uk.co.mruoc.idv.verificationcontext.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSms;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotification;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

import java.io.IOException;
import java.io.UncheckedIOException;

public class VerificationMethodSerializer extends JsonSerializer<VerificationMethod> {

    @Override
    public void serialize(final VerificationMethod method, final JsonGenerator json, final SerializerProvider provider) {
        toJson(method, json, provider);
    }

    private void toJson(final VerificationMethod method, final JsonGenerator json, final SerializerProvider provider) {
        if (method instanceof PushNotification) {
            toJson((PushNotification) method, json);
        } else if (method instanceof MobilePinsentry) {
            toJson((MobilePinsentry) method, json);
        } else if (method instanceof PhysicalPinsentry) {
            toJson((PhysicalPinsentry) method, json, provider);
        } else if (method instanceof OneTimePasscodeSms) {
            toJson((OneTimePasscodeSms) method, json, provider);
        } else {
            throw new VerificationMethodNotSerializableException(method.getName());
        }
    }

    private void toJson(final PushNotification method, final JsonGenerator json) {
        try {
            json.writeStartObject();
            writeCommonFields(method, json);
            json.writeEndObject();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void toJson(final MobilePinsentry method, final JsonGenerator json) {
        try {
            json.writeStartObject();
            writeCommonFields(method, json);
            json.writeStringField("function", method.getFunction().name());
            json.writeEndObject();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void toJson(final PhysicalPinsentry method, final JsonGenerator json, final SerializerProvider provider) {
        try {
            json.writeStartObject();
            writeCommonFields(method, json);
            json.writeStringField("function", method.getFunction().name());
            json.writeFieldName("cardNumbers");
            provider.defaultSerializeValue(method.getCardNumbers(), json);
            json.writeEndObject();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void toJson(final OneTimePasscodeSms method, final JsonGenerator json, final SerializerProvider provider) {
        try {
            json.writeStartObject();
            writeCommonFields(method, json);
            json.writeFieldName("passcodeSettings");
            provider.defaultSerializeValue(method.getPasscodeSettings(), json);
            json.writeFieldName("mobileNumbers");
            provider.defaultSerializeValue(method.getMobileNumbers(), json);
            json.writeEndObject();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void writeCommonFields(final VerificationMethod method, final JsonGenerator json) throws IOException {
        json.writeStringField("name", method.getName());
        json.writeNumberField("duration", method.getDuration().toMillis());
        json.writeBooleanField("eligible", method.isEligible());
    }

    public static class VerificationMethodNotSerializableException extends RuntimeException {

        public VerificationMethodNotSerializableException(final String methodName) {
            super(methodName);
        }

    }

}
