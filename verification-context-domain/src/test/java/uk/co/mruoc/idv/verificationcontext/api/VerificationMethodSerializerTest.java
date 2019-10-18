package uk.co.mruoc.idv.verificationcontext.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.FakeVerificationMethodEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.FakeVerificationMethodIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class VerificationMethodSerializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializeCommonFieldsForEligibleUnrecognisedMethod() throws JsonProcessingException {
        final VerificationMethod method = new FakeVerificationMethodEligible();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/fake-method-eligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeCommonFieldsForIneligibleUnrecognisedMethod() throws JsonProcessingException {
        final VerificationMethod method = new FakeVerificationMethodIneligible();

        final String json = MAPPER.writeValueAsString(method);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/fake-method-ineligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VerificationContextModule());
        return mapper;
    }

}
