package uk.co.idv.api.onetimepasscode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.onetimepasscode.ResendOneTimePasscodeRequestMother;
import uk.co.idv.json.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ResendOneTimePasscodeDocumentDeserializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new ApiOneTimePasscodeModuleProvider()).build();

    @Test
    void shouldDeserializeDocument() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("resend-one-time-passcode-request-document.json");

        final ResendOneTimePasscodeRequestDocument document = MAPPER.readValue(json, ResendOneTimePasscodeRequestDocument.class);

        final ResendOneTimePasscodeRequestDocument expectedDocument = new ResendOneTimePasscodeRequestDocument(ResendOneTimePasscodeRequestMother.build());
        assertThat(document).isEqualToComparingFieldByField(expectedDocument);
    }

}
