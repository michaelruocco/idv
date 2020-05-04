package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.PasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.PasscodeSettingsMother;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class PasscodeSettingsSerializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new OneTimePasscodeMethodModule()).build();

    @Test
    void shouldSerializePasscodeSettings() throws JsonProcessingException {
        final PasscodeSettings passcode = PasscodeSettingsMother.eligible();

        final String json = MAPPER.writeValueAsString(passcode);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/method/onetimepasscode/passcode-settings.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
