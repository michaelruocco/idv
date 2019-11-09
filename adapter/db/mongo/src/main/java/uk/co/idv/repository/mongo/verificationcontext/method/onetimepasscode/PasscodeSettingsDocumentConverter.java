package uk.co.idv.repository.mongo.verificationcontext.method.onetimepasscode;

import uk.co.mruoc.idv.verificationcontext.domain.model.method.DefaultPasscodeSettings;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PasscodeSettings;

public class PasscodeSettingsDocumentConverter {

    public PasscodeSettings toPasscodeSettings(final PasscodeSettingsDocument document) {
        return new DefaultPasscodeSettings();
    }

    public PasscodeSettingsDocument toDocument(final PasscodeSettings passcodeSettings) {
        final PasscodeSettingsDocument document = new PasscodeSettingsDocument();
        document.setLength(passcodeSettings.getLength());
        document.setDuration(passcodeSettings.getDuration().toMillis());
        document.setMaxGenerationAttempts(passcodeSettings.getMaxGenerationAttempts());
        return document;
    }

}
