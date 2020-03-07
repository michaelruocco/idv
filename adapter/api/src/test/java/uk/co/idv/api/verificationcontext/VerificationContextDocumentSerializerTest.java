package uk.co.idv.api.verificationcontext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.api.ApiObjectMapperSingleton;
import uk.co.idv.domain.entities.verificationcontext.VerificationContextMother;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class VerificationContextDocumentSerializerTest {

    private static final ObjectMapper MAPPER = ApiObjectMapperSingleton.instance();

    @Test
    void shouldSerializeContext() throws JsonProcessingException {
        final VerificationContext context = VerificationContextMother.fake();
        final VerificationContextDocument document = new VerificationContextDocument(context);

        final String json = MAPPER.writeValueAsString(document);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/verification-context-document.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
