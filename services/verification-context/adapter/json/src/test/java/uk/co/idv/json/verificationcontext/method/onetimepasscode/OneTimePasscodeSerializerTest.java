package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DefaultPasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethodMother;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeEligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.PasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultSuccessful;
import uk.co.idv.json.verificationcontext.VerificationContextModule;
import uk.co.mruoc.file.content.ContentLoader;

import java.util.Collection;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class OneTimePasscodeSerializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializeEligibleOneTimePasscode() throws JsonProcessingException {
        final Collection<DeliveryMethod> deliveryMethods = DeliveryMethodMother.oneSms();
        final PasscodeSettings passcodeSettings = new DefaultPasscodeSettings();
        final VerificationMethod method = new OneTimePasscodeEligible(passcodeSettings, deliveryMethods);

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = loadFileContent("one-time-passcode-eligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeEligibleOneTimePasscodeWithResult() throws JsonProcessingException {
        final VerificationResultSuccessful result = new FakeVerificationResultSuccessful(OneTimePasscode.NAME);
        final Collection<DeliveryMethod> deliveryMethods = DeliveryMethodMother.oneSms();
        final PasscodeSettings passcodeSettings = new DefaultPasscodeSettings();
        final VerificationMethod method = new OneTimePasscodeEligible(passcodeSettings, deliveryMethods, result);

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = loadFileContent("one-time-passcode-eligible-with-result.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeIneligibleOneTimePasscodeSms() throws JsonProcessingException {
        final VerificationMethod method = new OneTimePasscodeIneligible();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = loadFileContent("one-time-passcode-ineligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VerificationContextModule());
        return mapper;
    }

    private static String loadFileContent(final String name) {
        final String directory = "verification-context/method/onetimepasscode/%s";
        return ContentLoader.loadContentFromClasspath(String.format(directory, name));
    }

}
