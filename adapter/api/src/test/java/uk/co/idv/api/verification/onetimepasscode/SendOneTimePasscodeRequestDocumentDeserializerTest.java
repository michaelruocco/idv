package uk.co.idv.api.verification.onetimepasscode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.api.ApiObjectMapperSingleton;
import uk.co.idv.domain.usecases.onetimepasscode.SendOneTimePasscodeRequestMother;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class SendOneTimePasscodeRequestDocumentDeserializerTest {

    private static final ObjectMapper MAPPER = ApiObjectMapperSingleton.instance();

    @Test
    void shouldDeserializeDocument() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification/one-time-passcode/send-one-time-passcode-request-document.json");

        final SendOneTimePasscodeRequestDocument document = MAPPER.readValue(json, SendOneTimePasscodeRequestDocument.class);

        assertThat(document).isEqualToComparingFieldByField(new SendOneTimePasscodeRequestDocument(SendOneTimePasscodeRequestMother.build()));
    }

}
