package uk.co.idv.repository.mongo.verificationcontext.method.onetimepasscode;

import org.meanbean.lang.Factory;

public class PasscodeSettingsDocumentFactory implements Factory<PasscodeSettingsDocument> {

    @Override
    public PasscodeSettingsDocument create() {
        return PasscodeSettingsDocumentMother.build();
    }

}
