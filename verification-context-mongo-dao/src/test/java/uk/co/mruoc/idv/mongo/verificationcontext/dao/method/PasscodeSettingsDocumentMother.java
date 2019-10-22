package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

public class PasscodeSettingsDocumentMother {

    public static PasscodeSettingsDocument build() {
        final PasscodeSettingsDocument document = new PasscodeSettingsDocument();
        document.setLength(8);
        document.setDuration(150000);
        document.setMaxGenerationAttempts(3);
        return document;
    }

}
