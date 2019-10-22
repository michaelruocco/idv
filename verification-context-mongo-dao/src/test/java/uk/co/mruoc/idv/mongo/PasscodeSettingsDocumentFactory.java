package uk.co.mruoc.idv.mongo;

import org.meanbean.lang.Factory;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.PasscodeSettingsDocument;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.PasscodeSettingsDocumentMother;

public class PasscodeSettingsDocumentFactory implements Factory<PasscodeSettingsDocument> {

    @Override
    public PasscodeSettingsDocument create() {
        return PasscodeSettingsDocumentMother.build();
    }

}
