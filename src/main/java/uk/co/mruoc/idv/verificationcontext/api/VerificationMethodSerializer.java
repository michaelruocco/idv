package uk.co.mruoc.idv.verificationcontext.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobileNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSms;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotification;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public class VerificationMethodSerializer extends StdSerializer<VerificationMethod> {

    VerificationMethodSerializer() {
        super(VerificationMethod.class);
    }

    @Override
    public void serialize(final VerificationMethod method, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        writeJson(method, json, provider);
    }

    private void writeJson(final VerificationMethod method, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        if (method instanceof PushNotification) {
            writeJson((PushNotification) method, json);
        } else if (method instanceof MobilePinsentry) {
            writeJson((MobilePinsentry) method, json);
        } else if (method instanceof PhysicalPinsentry) {
            writeJson((PhysicalPinsentry) method, json, provider);
        } else if (method instanceof OneTimePasscodeSms) {
            writeJson((OneTimePasscodeSms) method, json, provider);
        } else {
            writeJson(method, json);
        }
    }

    private void writeJson(final PushNotification method, final JsonGenerator json) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        json.writeEndObject();
    }

    private void writeJson(final MobilePinsentry method, final JsonGenerator json) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        json.writeStringField("function", method.getFunction().name());
        json.writeEndObject();
    }

    private void writeJson(final PhysicalPinsentry method, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        json.writeStringField("function", method.getFunction().name());
        final Collection<CardNumber> cardNumbers = method.getCardNumbers();
        if (!cardNumbers.isEmpty()) {
            writeCardNumbersJson(cardNumbers, json, provider);
        }
        json.writeEndObject();
    }

    private void writeCardNumbersJson(final Collection<CardNumber> cardNumbers, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        json.writeFieldName("cardNumbers");
        provider.defaultSerializeValue(cardNumbers, json);
    }

    private void writeJson(final OneTimePasscodeSms method, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        json.writeFieldName("passcodeSettings");
        provider.defaultSerializeValue(method.getPasscodeSettings(), json);
        final Collection<MobileNumber> mobileNumbers = method.getMobileNumbers();
        if (!mobileNumbers.isEmpty()) {
            writeMobileNumbersJson(mobileNumbers, json, provider);
        }
        json.writeEndObject();
    }

    private void writeMobileNumbersJson(final Collection<MobileNumber> mobileNumbers, final JsonGenerator json, final SerializerProvider provider) throws IOException {
        json.writeFieldName("mobileNumbers");
        provider.defaultSerializeValue(mobileNumbers, json);
    }

    private void writeCommonFields(final VerificationMethod method, final JsonGenerator json) throws IOException {
        json.writeStringField("name", method.getName());
        json.writeNumberField("duration", method.getDuration().toMillis());
        json.writeBooleanField("eligible", method.isEligible());
        final Optional<String> reason = method.getEligibilityReason();
        if (reason.isPresent()) {
            json.writeStringField("reason", reason.get());
        }
    }

    private void writeJson(final VerificationMethod method, final JsonGenerator json) throws IOException {
        json.writeStartObject();
        writeCommonFields(method, json);
        json.writeEndObject();
    }

}
