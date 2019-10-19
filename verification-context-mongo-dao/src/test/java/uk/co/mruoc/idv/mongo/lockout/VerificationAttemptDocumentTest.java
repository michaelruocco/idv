package uk.co.mruoc.idv.mongo.lockout;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class VerificationAttemptDocumentTest {

    @Test
    void shouldTestBean() {
        new BeanTester().testBean(VerificationAttemptDocument.class);
    }

}
