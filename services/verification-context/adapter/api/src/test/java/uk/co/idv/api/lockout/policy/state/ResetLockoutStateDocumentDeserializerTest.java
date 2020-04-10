package uk.co.idv.api.lockout.policy.state;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.api.ApiTestObjectMapperFactory;
import uk.co.idv.api.lockout.state.ResetLockoutStateDocument;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ResetLockoutStateDocumentDeserializerTest {

    private static final ObjectMapper MAPPER = ApiTestObjectMapperFactory.build();

    @Test
    void shouldDeserializeDocument() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("lockout/reset-lockout-state-document.json");

        final ResetLockoutStateDocument document = MAPPER.readValue(json, ResetLockoutStateDocument.class);

        assertThat(document).isEqualToComparingFieldByField(new FakeResetLockoutStateDocument());
    }

}
