package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DefaultPasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.PasscodeSettings;
import uk.co.idv.json.verificationcontext.VerificationContextModule;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class PasscodeSettingsSerializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializePasscodeSettings() throws JsonProcessingException {
        final PasscodeSettings passcode = new DefaultPasscodeSettings();

        final String json = MAPPER.writeValueAsString(passcode);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/method/onetimepasscode/passcode-settings.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VerificationContextModule());
        return mapper;
    }

}