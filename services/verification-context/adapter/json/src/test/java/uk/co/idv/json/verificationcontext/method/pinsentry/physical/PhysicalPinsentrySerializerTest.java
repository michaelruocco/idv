package uk.co.idv.json.verificationcontext.method.pinsentry.physical;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.card.number.CardNumberMother;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.NoEligibleCards;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryIneligible;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultSuccessful;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import java.util.Collection;
import java.util.Collections;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction.RESPOND;

class PhysicalPinsentrySerializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new PhysicalPinsentryMethodModule()).build();

    @Test
    void shouldSerializeEligiblePhysicalPinsentry() throws JsonProcessingException {
        final Collection<CardNumber> cardNumbers = Collections.singleton(CardNumberMother.credit());
        final VerificationMethod method = new PhysicalPinsentryEligible(RESPOND, cardNumbers);

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = loadFileContent("physical-pinsentry-eligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeEligiblePhysicalPinsentryWithResult() throws JsonProcessingException {
        final VerificationResultSuccessful result = new FakeVerificationResultSuccessful(PhysicalPinsentry.NAME);
        final Collection<CardNumber> cardNumbers = Collections.singleton(CardNumberMother.credit());
        final VerificationMethod method = new PhysicalPinsentryEligible(RESPOND, cardNumbers, result);

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = loadFileContent("physical-pinsentry-eligible-with-result.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeIneligiblePhysicalPinsentry() throws JsonProcessingException {
        final VerificationMethod method = new PhysicalPinsentryIneligible(new NoEligibleCards(), RESPOND);

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = loadFileContent("physical-pinsentry-ineligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static String loadFileContent(final String name) {
        final String directory = "verification-context/method/pinsentry/physical/%s";
        return ContentLoader.loadContentFromClasspath(String.format(directory, name));
    }

}
