package uk.co.idv.api.onetimepasscode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerificationMother;
import uk.co.idv.json.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class OneTimePasscodeVerificationDocumentSerializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new ApiOneTimePasscodeModuleProvider()).build();

    @Test
    void shouldSerializeDocument() throws JsonProcessingException {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerificationMother.pending();
        final OneTimePasscodeVerificationDocument document = new OneTimePasscodeVerificationDocument(verification);

        final String json = MAPPER.writeValueAsString(document);

        final String expectedJson = ContentLoader.loadContentFromClasspath("one-time-passcode-verification-pending-document.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
