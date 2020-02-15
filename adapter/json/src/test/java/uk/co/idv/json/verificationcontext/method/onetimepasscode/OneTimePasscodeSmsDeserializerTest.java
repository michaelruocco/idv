package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DefaultPasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.MobileNumber;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.MobileNumberMother;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeSms;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeSmsEligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeSmsIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.PasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.json.ObjectMapperSingleton;
import uk.co.mruoc.file.content.ContentLoader;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class OneTimePasscodeSmsDeserializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.instance();

    @Test
    void shouldDeserializeIneligible() throws JsonProcessingException {
        final String json = loadFileContent("one-time-passcode-sms-ineligible.json");

        final VerificationMethod method =  MAPPER.readValue(json, VerificationMethod.class);

        final VerificationMethod expectedMethod = new OneTimePasscodeSmsIneligible();
        assertThat(method).isEqualToComparingFieldByField(expectedMethod);
    }

    @Test
    void shouldDeserializeEligible() throws JsonProcessingException {
        final String json = loadFileContent("one-time-passcode-sms-eligible.json");

        final OneTimePasscodeSmsEligible method = (OneTimePasscodeSmsEligible) MAPPER.readValue(json, VerificationMethod.class);

        final PasscodeSettings settings = new DefaultPasscodeSettings();
        final Collection<MobileNumber> mobileNumbers = MobileNumberMother.onePrimary();
        final OneTimePasscodeSmsEligible expectedMethod = new OneTimePasscodeSmsEligible(settings, mobileNumbers);
        assertThat(method).isEqualToIgnoringGivenFields(expectedMethod, "mobileNumbers");
        assertThat(method.getMobileNumbers()).containsExactlyElementsOf(expectedMethod.getMobileNumbers());
    }

    @Test
    void shouldDeserializeEligibleWithResult() throws JsonProcessingException {
        final String json = loadFileContent("one-time-passcode-sms-eligible-with-result.json");

        final OneTimePasscodeSmsEligible method = (OneTimePasscodeSmsEligible) MAPPER.readValue(json, VerificationMethod.class);

        final PasscodeSettings settings = new DefaultPasscodeSettings();
        final Collection<MobileNumber> mobileNumbers = MobileNumberMother.onePrimary();
        final OneTimePasscodeSmsEligible expectedMethod = new OneTimePasscodeSmsEligible(settings, mobileNumbers, new FakeVerificationResultSuccessful(OneTimePasscodeSms.NAME));
        assertThat(method).isEqualToIgnoringGivenFields(expectedMethod, "mobileNumbers");
        assertThat(method.getMobileNumbers()).containsExactlyElementsOf(expectedMethod.getMobileNumbers());
    }

    private static String loadFileContent(final String name) {
        final String directory = "verification-context/method/onetimepasscode/%s";
        return ContentLoader.loadContentFromClasspath(String.format(directory, name));
    }

}
