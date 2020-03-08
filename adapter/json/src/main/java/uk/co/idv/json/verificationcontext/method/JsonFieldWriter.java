package uk.co.idv.json.verificationcontext.method;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.PasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.cardnumber.CardNumber;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.Optional;

public class JsonFieldWriter {

    private JsonFieldWriter() {
        // utility class
    }

    public static void writeName(final String name, final JsonGenerator json) throws IOException {
        json.writeStringField("name", name);
    }

    public static void writeDuration(final Duration duration, final JsonGenerator json) throws IOException {
        json.writeNumberField("duration", duration.toMillis());
    }

    public static void writeFunction(final PinsentryFunction function, final JsonGenerator json) throws IOException {
        json.writeStringField("function", function.name().toLowerCase());
    }

    public static void writeComplete(final boolean complete, final JsonGenerator json) throws IOException {
        json.writeBooleanField("complete", complete);
    }

    public static void writeSuccessful(final boolean successful, final JsonGenerator json) throws IOException {
        json.writeBooleanField("successful", successful);
    }

    public static void writeMaxAttempts(final int maxAttempts, final JsonGenerator json) throws IOException {
        json.writeNumberField("maxAttempts", maxAttempts);
    }

    public static void writeEligibility(final Eligibility eligibility, final JsonGenerator json) throws IOException {
        json.writeBooleanField("eligible", eligibility.isEligible());
        final Optional<String> reason = eligibility.getReason();
        if (reason.isPresent()) {
            json.writeStringField("reason", reason.get());
        }
    }

    public static void writeResults(final VerificationResults results,
                             final JsonGenerator json,
                             final SerializerProvider provider) throws IOException {
        json.writeFieldName("results");
        provider.defaultSerializeValue(results, json);
    }

    public static void writeCardNumbers(final Collection<CardNumber> cardNumbers,
                                        final JsonGenerator json,
                                        final SerializerProvider provider) throws IOException {
        json.writeFieldName("cardNumbers");
        provider.defaultSerializeValue(cardNumbers, json);
    }

    public static void writePasscodeSettings(final PasscodeSettings passcode,
                                      final JsonGenerator json,
                                      final SerializerProvider provider) throws IOException {
        json.writeFieldName("passcodeSettings");
        provider.defaultSerializeValue(passcode, json);
    }

    public static void writeDeliveryMethods(final Collection<DeliveryMethod> deliveryMethods,
                                            final JsonGenerator json,
                                            final SerializerProvider provider) throws IOException {
        json.writeFieldName("deliveryMethods");
        provider.defaultSerializeValue(deliveryMethods, json);
    }

}
