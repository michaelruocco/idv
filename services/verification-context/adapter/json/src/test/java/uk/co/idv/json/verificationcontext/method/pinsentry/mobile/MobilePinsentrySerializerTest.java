
package uk.co.idv.json.verificationcontext.method.pinsentry.mobile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryMother;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsMother;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class MobilePinsentrySerializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new MobilePinsentryMethodModule()).build();

    @Test
    void shouldSerializeEligibleMobilePinsentry() throws JsonProcessingException {
        final VerificationMethod method = MobilePinsentryMother.eligible();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = loadFileContent("mobile-pinsentry-eligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeEligibleMobilePinsentryWithResult() throws JsonProcessingException {
        final VerificationMethod method = MobilePinsentryMother.eligibleBuilder()
                .results(VerificationResultsMother.oneSuccessful(MobilePinsentry.NAME))
                .build();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = loadFileContent("mobile-pinsentry-eligible-with-result.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeIneligibleMobilePinsentry() throws JsonProcessingException {
        final VerificationMethod method = MobilePinsentryMother.ineligible();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = loadFileContent("mobile-pinsentry-ineligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static String loadFileContent(final String name) {
        final String directory = "verification-context/method/pinsentry/mobile/%s";
        return ContentLoader.loadContentFromClasspath(String.format(directory, name));
    }

}
