package uk.co.idv.api.verification.onetimepasscode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.api.ApiObjectMapperSingleton;
import uk.co.idv.domain.usecases.onetimepasscode.VerifyOneTimePasscodeRequestMother;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class VerifyOneTimePasscodeDocumentDeserializerTest {

    private static final ObjectMapper MAPPER = ApiObjectMapperSingleton.instance();

    @Test
    void shouldDeserializeDocument() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification/one-time-passcode/verify-one-time-passcode-request-document.json");

        final VerifyOneTimePasscodeRequestDocument document = MAPPER.readValue(json, VerifyOneTimePasscodeRequestDocument.class);

        assertThat(document).isEqualToComparingFieldByField(new VerifyOneTimePasscodeRequestDocument(VerifyOneTimePasscodeRequestMother.build()));
    }

}
