package uk.co.idv.json.verificationcontext.method.pinsentry.physical;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryMother;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsMother;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static org.assertj.core.api.Assertions.assertThat;

class PhysicalPinsentryDeserializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new PhysicalPinsentryMethodModule()).build();

    @Test
    void shouldDeserializeIneligible() throws JsonProcessingException {
        final String json = loadFileContent("physical-pinsentry-ineligible.json");

        final PhysicalPinsentry method = (PhysicalPinsentry) MAPPER.readValue(json, VerificationMethod.class);

        final PhysicalPinsentry expectedMethod = PhysicalPinsentryMother.ineligible();
        assertThat(method).isEqualToIgnoringGivenFields(expectedMethod, "params");
        assertThat(method.getParams()).isEqualToComparingFieldByField(expectedMethod.getParams());
    }

    @Test
    void shouldDeserializeEligible() throws JsonProcessingException {
        final String json = loadFileContent("physical-pinsentry-eligible.json");

        final PhysicalPinsentry method = (PhysicalPinsentry) MAPPER.readValue(json, VerificationMethod.class);

        final PhysicalPinsentry expectedMethod = PhysicalPinsentryMother.eligible();
        assertThat(method).isEqualToIgnoringGivenFields(expectedMethod, "cardNumbers");
        assertThat(method.getCardNumbers()).containsExactlyElementsOf(expectedMethod.getCardNumbers());
    }

    @Test
    void shouldDeserializeEligibleWithResult() throws JsonProcessingException {
        final String json = loadFileContent("physical-pinsentry-eligible-with-result.json");

        final PhysicalPinsentry method = (PhysicalPinsentry) MAPPER.readValue(json, VerificationMethod.class);

        final PhysicalPinsentry expectedMethod = PhysicalPinsentryMother.eligibleBuilder()
                .results(VerificationResultsMother.oneSuccessful(PhysicalPinsentry.NAME))
                .build();
        assertThat(method).isEqualToIgnoringGivenFields(expectedMethod, "cardNumbers");
        assertThat(method.getCardNumbers()).containsExactlyElementsOf(expectedMethod.getCardNumbers());
    }

    private static String loadFileContent(final String name) {
        final String directory = "verification-context/method/pinsentry/physical/%s";
        return ContentLoader.loadContentFromClasspath(String.format(directory, name));
    }

}
