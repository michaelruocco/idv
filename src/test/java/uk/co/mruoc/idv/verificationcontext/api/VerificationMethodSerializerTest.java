package uk.co.mruoc.idv.verificationcontext.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.DefaultPasscodeSettings;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.Eligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.FakeVerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobileNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.NoEligibleCards;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.NoMobileApplication;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSmsEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSmsIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PasscodeSettings;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentryEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentryIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotification;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static uk.co.mruoc.idv.verificationcontext.domain.model.method.PinsentryFunction.RESPOND;

class VerificationMethodSerializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializeEligiblePushNotification() throws JsonProcessingException {
        final VerificationMethod method = new PushNotification(new Eligible());

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/push-notification-eligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeIneligiblePushNotification() throws JsonProcessingException {
        final VerificationMethod method = new PushNotification(new NoMobileApplication());

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/push-notification-ineligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeEligiblePhysicalPinsentry() throws JsonProcessingException {
        final Collection<CardNumber> cardNumbers = Collections.singleton(new CardNumber(UUID.fromString("6c880ce6-0d3c-4ac7-b419-8c2dce645cfa"), "4929991234567890"));
        final VerificationMethod method = new PhysicalPinsentryEligible(RESPOND, cardNumbers);

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/physical-pinsentry-eligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeIneligiblePhysicalPinsentry() throws JsonProcessingException {
        final VerificationMethod method = new PhysicalPinsentryIneligible(new NoEligibleCards(), RESPOND);

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/physical-pinsentry-ineligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeEligibleMobilePinsentry() throws JsonProcessingException {
        final VerificationMethod method = new MobilePinsentry(new Eligible(), RESPOND);

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/mobile-pinsentry-eligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeIneligibleMobilePinsentry() throws JsonProcessingException {
        final VerificationMethod method = new MobilePinsentry(new NoMobileApplication(), RESPOND);

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/mobile-pinsentry-ineligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeEligibleOneTimePasscodeSms() throws JsonProcessingException {
        final Collection<MobileNumber> mobileNumbers = Collections.singleton(new MobileNumber(UUID.fromString("2a82fcb5-19d4-469d-9c1b-4b2318c1e3f4"), "07809385580"));
        final PasscodeSettings passcodeSettings = new DefaultPasscodeSettings();
        final VerificationMethod method = new OneTimePasscodeSmsEligible(passcodeSettings, mobileNumbers);

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/one-time-passcode-sms-eligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeIneligibleOneTimePasscodeSms() throws JsonProcessingException {
        final PasscodeSettings passcodeSettings = new DefaultPasscodeSettings();
        final VerificationMethod method = new OneTimePasscodeSmsIneligible(passcodeSettings);

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/one-time-passcode-sms-ineligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeCommonFieldsForUnrecognisedMethod() throws JsonProcessingException {
        final VerificationMethod method = new FakeVerificationMethod();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/fake-method.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VerificationContextModule());
        return mapper;
    }

}
