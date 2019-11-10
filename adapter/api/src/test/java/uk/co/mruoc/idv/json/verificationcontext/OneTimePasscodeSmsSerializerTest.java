package uk.co.mruoc.idv.json.verificationcontext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DefaultPasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.MobileNumber;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeSms;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeSmsEligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeSmsIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.PasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultSuccessful;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class OneTimePasscodeSmsSerializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

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
    void shouldSerializeEligibleOneTimePasscodeSmsWithResult() throws JsonProcessingException {
        final VerificationResultSuccessful result = new FakeVerificationResultSuccessful(OneTimePasscodeSms.NAME);
        final Collection<MobileNumber> mobileNumbers = Collections.singleton(new MobileNumber(UUID.fromString("2a82fcb5-19d4-469d-9c1b-4b2318c1e3f4"), "07809385580"));
        final PasscodeSettings passcodeSettings = new DefaultPasscodeSettings();
        final VerificationMethod method = new OneTimePasscodeSmsEligible(passcodeSettings, mobileNumbers, result);

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/one-time-passcode-sms-eligible-with-result.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeIneligibleOneTimePasscodeSms() throws JsonProcessingException {
        final VerificationMethod method = new OneTimePasscodeSmsIneligible();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/one-time-passcode-sms-ineligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VerificationContextModule());
        return mapper;
    }

}
