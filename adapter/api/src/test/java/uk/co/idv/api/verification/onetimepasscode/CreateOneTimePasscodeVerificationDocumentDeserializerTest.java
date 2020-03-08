package uk.co.idv.api.verification.onetimepasscode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.api.ApiObjectMapperSingleton;
import uk.co.idv.domain.usecases.verification.onetimepasscode.CreateOneTimePasscodeVerificationRequestMother;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class CreateOneTimePasscodeVerificationDocumentDeserializerTest {

    private static final ObjectMapper MAPPER = ApiObjectMapperSingleton.instance();

    @Test
    void shouldDeserializeDocument() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification/one-time-passcode/create-one-time-passcode-verification-request-document.json");

        final CreateOneTimePasscodeVerificationRequestDocument document = MAPPER.readValue(json, CreateOneTimePasscodeVerificationRequestDocument.class);

        assertThat(document).isEqualToComparingFieldByField(new CreateOneTimePasscodeVerificationRequestDocument(CreateOneTimePasscodeVerificationRequestMother.build()));
    }

}
