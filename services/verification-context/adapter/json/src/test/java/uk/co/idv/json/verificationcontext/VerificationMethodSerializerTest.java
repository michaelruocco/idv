package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.FakeVerificationMethodEligible;
import uk.co.idv.domain.entities.verificationcontext.method.FakeVerificationMethodIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import java.util.Arrays;
import java.util.Collection;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class VerificationMethodSerializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(modules()).build();

    @Test
    void shouldSerializeCommonFieldsForEligibleUnrecognisedMethod() throws JsonProcessingException {
        final VerificationMethod method = new FakeVerificationMethodEligible();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = loadFileContent("fake-method-eligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeCommonFieldsForIneligibleUnrecognisedMethod() throws JsonProcessingException {
        final VerificationMethod method = new FakeVerificationMethodIneligible();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = loadFileContent("fake-method-ineligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static Collection<Module> modules() {
        return Arrays.asList(
                new VerificationContextModule(),
                new AllowAllSimpleMethodModule()
        );
    }

    private static String loadFileContent(final String name) {
        final String directory = "verification-context/method/%s";
        return ContentLoader.loadContentFromClasspath(String.format(directory, name));
    }

}
