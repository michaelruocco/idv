package uk.co.idv.repository.mongo.verificationcontext.method.onetimepasscode;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class MobileNumberDocumentTest {

    @Test
    void shouldTestBean() {
        new BeanTester().testBean(MobileNumberDocument.class);
    }

}
