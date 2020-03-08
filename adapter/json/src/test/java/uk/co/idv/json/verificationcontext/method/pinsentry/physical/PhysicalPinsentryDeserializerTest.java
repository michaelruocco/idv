package uk.co.idv.json.verificationcontext.method.pinsentry.physical;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.cardnumber.CardNumberMother;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.NoEligibleCards;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryIneligible;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.json.ObjectMapperSingleton;
import uk.co.mruoc.file.content.ContentLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction.RESPOND;

class PhysicalPinsentryDeserializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.instance();

    @Test
    void shouldDeserializeIneligible() throws JsonProcessingException {
        final String json = loadFileContent("physical-pinsentry-ineligible.json");

        final VerificationMethod method =  MAPPER.readValue(json, VerificationMethod.class);

        final VerificationMethod expectedMethod = new PhysicalPinsentryIneligible(new NoEligibleCards(), RESPOND);
        assertThat(method).isEqualToComparingFieldByField(expectedMethod);
    }

    @Test
    void shouldDeserializeEligible() throws JsonProcessingException {
        final String json = loadFileContent("physical-pinsentry-eligible.json");

        final PhysicalPinsentryEligible method =  (PhysicalPinsentryEligible) MAPPER.readValue(json, VerificationMethod.class);

        final PhysicalPinsentryEligible expectedMethod = new PhysicalPinsentryEligible(RESPOND, CardNumberMother.oneCredit());
        assertThat(method).isEqualToIgnoringGivenFields(expectedMethod, "cardNumbers");
        assertThat(method.getCardNumbers()).containsExactlyElementsOf(expectedMethod.getCardNumbers());
    }

    @Test
    void shouldDeserializeEligibleWithResult() throws JsonProcessingException {
        final String json = loadFileContent("physical-pinsentry-eligible-with-result.json");

        final PhysicalPinsentryEligible method = (PhysicalPinsentryEligible) MAPPER.readValue(json, VerificationMethod.class);

        final PhysicalPinsentryEligible expectedMethod = new PhysicalPinsentryEligible(RESPOND, CardNumberMother.oneCredit(), new FakeVerificationResultSuccessful(PhysicalPinsentry.NAME));
        assertThat(method).isEqualToIgnoringGivenFields(expectedMethod, "cardNumbers");
        assertThat(method.getCardNumbers()).containsExactlyElementsOf(expectedMethod.getCardNumbers());
    }

    private static String loadFileContent(final String name) {
        final String directory = "verification-context/method/pinsentry/physical/%s";
        return ContentLoader.loadContentFromClasspath(String.format(directory, name));
    }

}
