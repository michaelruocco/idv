package uk.co.idv.repository.mongo.verificationcontext.method.onetimepasscode;

public class PasscodeSettingsDocumentMother {

    private PasscodeSettingsDocumentMother() {
        // utility class
    }

    public static PasscodeSettingsDocument build() {
        final PasscodeSettingsDocument document = new PasscodeSettingsDocument();
        document.setLength(8);
        document.setDuration(150000);
        document.setMaxGenerationAttempts(3);
        return document;
    }

}
