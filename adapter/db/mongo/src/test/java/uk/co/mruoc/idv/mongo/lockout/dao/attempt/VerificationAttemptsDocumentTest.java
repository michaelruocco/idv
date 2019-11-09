package uk.co.mruoc.idv.mongo.lockout.dao.attempt;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class VerificationAttemptsDocumentTest {

    @Test
    void shouldTestBean() {
        new BeanTester().testBean(VerificationAttemptsDocument.class);
    }

}
