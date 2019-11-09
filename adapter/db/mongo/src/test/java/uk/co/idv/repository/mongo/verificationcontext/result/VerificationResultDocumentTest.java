package uk.co.idv.repository.mongo.verificationcontext.result;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class VerificationResultDocumentTest {

    @Test
    void shouldTestBean() {
        new BeanTester().testBean(VerificationResultDocument.class);
    }

}
