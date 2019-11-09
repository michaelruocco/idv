package uk.co.mruoc.idv.mongo.verificationcontext.dao.result;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class VerificationResultDocumentTest {

    @Test
    void shouldTestBean() {
        new BeanTester().testBean(VerificationResultDocument.class);
    }

}
