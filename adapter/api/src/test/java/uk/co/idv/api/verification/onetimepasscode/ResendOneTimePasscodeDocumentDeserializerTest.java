package uk.co.idv.api.verification.onetimepasscode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.api.ApiObjectMapperSingleton;
import uk.co.idv.domain.usecases.verification.onetimepasscode.ResendOneTimePasscodeRequestMother;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ResendOneTimePasscodeDocumentDeserializerTest {

    private static final ObjectMapper MAPPER = ApiObjectMapperSingleton.instance();

    @Test
    void shouldDeserializeDocument() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification/one-time-passcode/resend-one-time-passcode-request-document.json");

        final ResendOneTimePasscodeRequestDocument document = MAPPER.readValue(json, ResendOneTimePasscodeRequestDocument.class);

        final ResendOneTimePasscodeRequestDocument expectedDocument = new ResendOneTimePasscodeRequestDocument(ResendOneTimePasscodeRequestMother.build());
        assertThat(document).isEqualToComparingFieldByField(expectedDocument);
    }

}
