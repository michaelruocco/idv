package uk.co.mruoc.idv.verificationcontext.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.NoEligibleCards;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentryEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentryIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static uk.co.mruoc.idv.verificationcontext.domain.model.method.PinsentryFunction.RESPOND;

class PhysicalPinsentrySerializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

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

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VerificationContextModule());
        return mapper;
    }

}
