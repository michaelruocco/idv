package uk.co.mruoc.idv.verificationcontext.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.Eligibility;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobileNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PasscodeSettings;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PinsentryFunction;

import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.Optional;

class JsonFieldWriter {

    static void writeName(final String name, final JsonGenerator json) throws IOException {
        json.writeStringField("name", name);
    }

    static void writeDuration(final Duration duration, final JsonGenerator json) throws IOException {
        json.writeNumberField("duration", duration.toMillis());
    }

    static void writeFunction(final PinsentryFunction function, final JsonGenerator json) throws IOException {
        json.writeStringField("function", function.name());
    }

    static void writeEligibility(final Eligibility eligibility, final JsonGenerator json) throws IOException {
        json.writeBooleanField("eligible", eligibility.isEligible());
        final Optional<String> reason = eligibility.getReason();
        if (reason.isPresent()) {
            json.writeStringField("reason", reason.get());
        }
    }

    static void writeCardNumbersJson(final Collection<CardNumber> cardNumbers,
                                     final JsonGenerator json,
                                     final SerializerProvider provider) throws IOException {
        json.writeFieldName("cardNumbers");
        provider.defaultSerializeValue(cardNumbers, json);
    }

    static void writePasscodeSettings(final PasscodeSettings passcode,
                                      final JsonGenerator json,
                                      final SerializerProvider provider) throws IOException {
        json.writeFieldName("passcodeSettings");
        provider.defaultSerializeValue(passcode, json);
    }

    static void writeMobileNumbersJson(final Collection<MobileNumber> mobileNumbers,
                                       final JsonGenerator json,
                                       final SerializerProvider provider) throws IOException {
        json.writeFieldName("mobileNumbers");
        provider.defaultSerializeValue(mobileNumbers, json);
    }

}
