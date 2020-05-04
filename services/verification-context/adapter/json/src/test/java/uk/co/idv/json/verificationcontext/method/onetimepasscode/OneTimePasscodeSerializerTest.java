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

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class OneTimePasscodeSerializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new OneTimePasscodeMethodModule()).build();

    @Test
    void shouldSerializeEligibleOneTimePasscode() throws JsonProcessingException {
        final VerificationMethod method = OneTimePasscodeMother.eligible();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = loadFileContent("one-time-passcode-eligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeEligibleOneTimePasscodeWithResult() throws JsonProcessingException {
        final VerificationMethod method = OneTimePasscodeMother.eligibleBuilder()
                .results(VerificationResultsMother.oneSuccessful(OneTimePasscode.NAME))
                .build();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = loadFileContent("one-time-passcode-eligible-with-result.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeIneligibleOneTimePasscodeSms() throws JsonProcessingException {
        final VerificationMethod method = OneTimePasscodeMother.ineligible();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = loadFileContent("one-time-passcode-ineligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static String loadFileContent(final String name) {
        final String directory = "verification-context/method/onetimepasscode/%s";
        return ContentLoader.loadContentFromClasspath(String.format(directory, name));
    }

}
