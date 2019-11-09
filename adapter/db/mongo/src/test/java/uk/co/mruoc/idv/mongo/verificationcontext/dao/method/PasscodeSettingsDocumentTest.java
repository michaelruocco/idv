package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class PasscodeSettingsDocumentTest {

    @Test
    void shouldTestBean() {
        new BeanTester().testBean(PasscodeSettingsDocument.class);
    }

}
