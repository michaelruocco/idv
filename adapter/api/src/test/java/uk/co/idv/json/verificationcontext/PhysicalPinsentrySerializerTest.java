package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.CardNumber;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.CardNumberMother;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.NoEligibleCards;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultSuccessful;

import java.util.Collection;
import java.util.Collections;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction.RESPOND;

class PhysicalPinsentrySerializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializeEligiblePhysicalPinsentry() throws JsonProcessingException {
        final Collection<CardNumber> cardNumbers = Collections.singleton(CardNumberMother.credit());
        final VerificationMethod method = new PhysicalPinsentryEligible(RESPOND, cardNumbers);

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/physical-pinsentry-eligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeEligiblePhysicalPinsentryWithResult() throws JsonProcessingException {
        final VerificationResultSuccessful result = new FakeVerificationResultSuccessful(PhysicalPinsentry.NAME);
        final Collection<CardNumber> cardNumbers = Collections.singleton(CardNumberMother.credit());
        final VerificationMethod method = new PhysicalPinsentryEligible(RESPOND, cardNumbers, result);

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/physical-pinsentry-eligible-with-result.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeIneligiblePhysicalPinsentry() throws JsonProcessingException {
        final VerificationMethod method = new PhysicalPinsentryIneligible(new NoEligibleCards(), RESPOND);

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/physical-pinsentry-ineligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VerificationContextModule());
        return mapper;
    }

}
