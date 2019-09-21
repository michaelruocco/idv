package uk.co.mruoc.idv.verificationcontext.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.DefaultPasscodeSettings;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PasscodeSettings;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class PasscodeSettingsSerializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializeIdentity() throws JsonProcessingException {
        final PasscodeSettings passcode = new DefaultPasscodeSettings();

        final String json = MAPPER.writeValueAsString(passcode);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/passcode-settings.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VerificationContextModule());
        return mapper;
    }

}
