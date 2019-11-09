package uk.co.idv.repository.mongo.verificationcontext.method.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.DefaultPasscodeSettings;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PasscodeSettings;

import static org.assertj.core.api.Assertions.assertThat;

class PasscodeSettingsDocumentConverterTest {

    private final PasscodeSettingsDocumentConverter converter = new PasscodeSettingsDocumentConverter();

    @Test
    void shouldReturnDefaultPasscodeSettings() {
        final PasscodeSettingsDocument document = PasscodeSettingsDocumentMother.build();

        final PasscodeSettings settings = converter.toPasscodeSettings(document);

        assertThat(settings).isInstanceOf(DefaultPasscodeSettings.class);
    }

    @Test
    void shouldPopulateLengthOnDocument() {
        final PasscodeSettings settings = new DefaultPasscodeSettings();

        final PasscodeSettingsDocument document = converter.toDocument(settings);

        assertThat(document.getLength()).isEqualTo(settings.getLength());
    }

    @Test
    void shouldPopulateDurationOnDocument() {
        final PasscodeSettings settings = new DefaultPasscodeSettings();

        final PasscodeSettingsDocument document = converter.toDocument(settings);

        assertThat(document.getDuration()).isEqualTo(settings.getDuration().toMillis());
    }

    @Test
    void shouldPopulateMaxGenerationAttemptsOnDocument() {
        final PasscodeSettings settings = new DefaultPasscodeSettings();

        final PasscodeSettingsDocument document = converter.toDocument(settings);

        assertThat(document.getMaxGenerationAttempts()).isEqualTo(settings.getMaxGenerationAttempts());
    }

}
