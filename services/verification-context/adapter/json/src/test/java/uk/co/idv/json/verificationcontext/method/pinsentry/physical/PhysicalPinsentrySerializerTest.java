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

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class PhysicalPinsentrySerializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new PhysicalPinsentryMethodModule()).build();

    @Test
    void shouldSerializeEligiblePhysicalPinsentry() throws JsonProcessingException {
        final VerificationMethod method = PhysicalPinsentryMother.eligible();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = loadFileContent("physical-pinsentry-eligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeEligiblePhysicalPinsentryWithResult() throws JsonProcessingException {
        final VerificationMethod method = PhysicalPinsentryMother.eligibleBuilder()
                .results(VerificationResultsMother.oneSuccessful(PhysicalPinsentry.NAME))
                .build();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = loadFileContent("physical-pinsentry-eligible-with-result.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeIneligiblePhysicalPinsentry() throws JsonProcessingException {
        final VerificationMethod method = PhysicalPinsentryMother.ineligible();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = loadFileContent("physical-pinsentry-ineligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static String loadFileContent(final String name) {
        final String directory = "verification-context/method/pinsentry/physical/%s";
        return ContentLoader.loadContentFromClasspath(String.format(directory, name));
    }

}
