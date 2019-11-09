package uk.co.idv.repository.mongo.verificationcontext.method.onetimepasscode;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class PasscodeSettingsDocumentTest {

    @Test
    void shouldTestBean() {
        new BeanTester().testBean(PasscodeSettingsDocument.class);
    }

}
