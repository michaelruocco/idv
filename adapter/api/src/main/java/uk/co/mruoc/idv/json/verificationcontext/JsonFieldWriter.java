package uk.co.mruoc.idv.json.verificationcontext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.CardNumber;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.MobileNumber;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.PasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

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
        json.writeStringField("function", function.name().toLowerCase());
    }

    static void writeComplete(final boolean complete, final JsonGenerator json) throws IOException {
        json.writeBooleanField("complete", complete);
    }

    static void writeSuccessful(final boolean successful, final JsonGenerator json) throws IOException {
        json.writeBooleanField("successful", successful);
    }

    static void writeMaxAttempts(final int maxAttempts, final JsonGenerator json) throws IOException {
        json.writeNumberField("maxAttempts", maxAttempts);
    }

    static void writeEligibility(final Eligibility eligibility, final JsonGenerator json) throws IOException {
        json.writeBooleanField("eligible", eligibility.isEligible());
        final Optional<String> reason = eligibility.getReason();
        if (reason.isPresent()) {
            json.writeStringField("reason", reason.get());
        }
    }

    static void writeResults(final VerificationResults results,
                             final JsonGenerator json,
                             final SerializerProvider provider) throws IOException {
        json.writeFieldName("results");
        provider.defaultSerializeValue(results, json);
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