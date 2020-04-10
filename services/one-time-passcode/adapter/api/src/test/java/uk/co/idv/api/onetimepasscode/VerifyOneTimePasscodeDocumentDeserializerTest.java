package uk.co.idv.api.onetimepasscode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.onetimepasscode.VerifyOneTimePasscodeRequestMother;
import uk.co.idv.json.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class VerifyOneTimePasscodeDocumentDeserializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new ApiOneTimePasscodeModuleProvider()).build();

    @Test
    void shouldDeserializeDocument() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verify-one-time-passcode-request-document.json");

        final VerifyOneTimePasscodeRequestDocument document = MAPPER.readValue(json, VerifyOneTimePasscodeRequestDocument.class);

        assertThat(document).isEqualToComparingFieldByField(new VerifyOneTimePasscodeRequestDocument(VerifyOneTimePasscodeRequestMother.build()));
    }

}
