package uk.co.idv.api.lockout.policy.state;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.api.verificationcontext.ApiVerificationContextObjectMapperFactory;
import uk.co.idv.api.lockout.state.LockoutStateDocument;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.idv.domain.entities.lockout.policy.hard.FakeHardLockoutState;

import java.io.IOException;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class ApiHardLockoutStateMixinTest {

    private static final ObjectMapper MAPPER = new ApiVerificationContextObjectMapperFactory().build();

    @Test
    void shouldSerializeDocument() throws IOException {
        final LockoutStateDocument document = new LockoutStateDocument(new FakeHardLockoutState());

        final String json = MAPPER.writeValueAsString(document);

        final String expectedJson = ContentLoader.loadContentFromClasspath("lockout/hard/hard-lockout-state-document.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
