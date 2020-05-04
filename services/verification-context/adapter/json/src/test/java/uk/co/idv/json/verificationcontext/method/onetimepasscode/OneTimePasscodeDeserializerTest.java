package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeMother;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsMother;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static org.assertj.core.api.Assertions.assertThat;

class OneTimePasscodeDeserializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new OneTimePasscodeMethodModule()).build();

    @Test
    void shouldDeserializeIneligible() throws JsonProcessingException {
        final String json = loadFileContent("one-time-passcode-ineligible.json");

        final VerificationMethod method = MAPPER.readValue(json, VerificationMethod.class);

        final VerificationMethod expectedMethod = OneTimePasscodeMother.ineligible();
        assertThat(method).isEqualToComparingFieldByField(expectedMethod);
    }

    @Test
    void shouldDeserializeEligible() throws JsonProcessingException {
        final String json = loadFileContent("one-time-passcode-eligible.json");

        final OneTimePasscode method = (OneTimePasscode) MAPPER.readValue(json, VerificationMethod.class);

        final OneTimePasscode expectedMethod = OneTimePasscodeMother.eligible();
        assertThat(method).isEqualToIgnoringGivenFields(expectedMethod, "deliveryMethods");
        assertThat(method.getDeliveryMethods()).containsExactlyElementsOf(expectedMethod.getDeliveryMethods());
    }

    @Test
    void shouldDeserializeEligibleWithResult() throws JsonProcessingException {
        final String json = loadFileContent("one-time-passcode-eligible-with-result.json");

        final OneTimePasscode method = (OneTimePasscode) MAPPER.readValue(json, VerificationMethod.class);

        final OneTimePasscode expectedMethod = OneTimePasscodeMother.eligibleBuilder()
                .results(VerificationResultsMother.oneSuccessful(OneTimePasscode.NAME))
                .build();
        assertThat(method).isEqualToIgnoringGivenFields(expectedMethod, "deliveryMethods");
        assertThat(method.getDeliveryMethods()).containsExactlyElementsOf(expectedMethod.getDeliveryMethods());
    }

    private static String loadFileContent(final String name) {
        final String directory = "verification-context/method/onetimepasscode/%s";
        return ContentLoader.loadContentFromClasspath(String.format(directory, name));
    }

}
