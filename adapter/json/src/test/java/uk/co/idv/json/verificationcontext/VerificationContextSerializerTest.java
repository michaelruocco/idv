package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationContextMother;
import uk.co.idv.json.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class VerificationContextSerializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory().build();

    @Test
    void shouldSerializeContext() throws JsonProcessingException {
        final VerificationContext context = VerificationContextMother.fake();

        final String json = MAPPER.writeValueAsString(context);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/verification-context.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
