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
import uk.co.idv.json.TestObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class OneTimePasscodeDeserializerTest {

    private static final ObjectMapper MAPPER = TestObjectMapperFactory.build();

    @Test
    void shouldDeserializeIneligible() throws JsonProcessingException {
        final String json = loadFileContent("one-time-passcode-ineligible.json");

        final VerificationMethod method =  MAPPER.readValue(json, VerificationMethod.class);

        final VerificationMethod expectedMethod = new OneTimePasscodeIneligible();
        assertThat(method).isEqualToComparingFieldByField(expectedMethod);
    }

    @Test
    void shouldDeserializeEligible() throws JsonProcessingException {
        final String json = loadFileContent("one-time-passcode-eligible.json");

        final OneTimePasscodeEligible method = (OneTimePasscodeEligible) MAPPER.readValue(json, VerificationMethod.class);

        final PasscodeSettings settings = new DefaultPasscodeSettings();
        final Collection<DeliveryMethod> deliveryMethods = DeliveryMethodMother.oneSms();
        final OneTimePasscodeEligible expectedMethod = new OneTimePasscodeEligible(settings, deliveryMethods);
        assertThat(method).isEqualToIgnoringGivenFields(expectedMethod, "deliveryMethods");
        assertThat(method.getDeliveryMethods()).containsExactlyElementsOf(expectedMethod.getDeliveryMethods());
    }

    @Test
    void shouldDeserializeEligibleWithResult() throws JsonProcessingException {
        final String json = loadFileContent("one-time-passcode-eligible-with-result.json");

        final OneTimePasscodeEligible method = (OneTimePasscodeEligible) MAPPER.readValue(json, VerificationMethod.class);

        final PasscodeSettings settings = new DefaultPasscodeSettings();
        final Collection<DeliveryMethod> deliveryMethods = DeliveryMethodMother.oneSms();
        final OneTimePasscodeEligible expectedMethod = new OneTimePasscodeEligible(settings, deliveryMethods, new FakeVerificationResultSuccessful(OneTimePasscode.NAME));
        assertThat(method).isEqualToIgnoringGivenFields(expectedMethod, "deliveryMethods");
        assertThat(method.getDeliveryMethods()).containsExactlyElementsOf(expectedMethod.getDeliveryMethods());
    }

    private static String loadFileContent(final String name) {
        final String directory = "verification-context/method/onetimepasscode/%s";
        return ContentLoader.loadContentFromClasspath(String.format(directory, name));
    }

}
