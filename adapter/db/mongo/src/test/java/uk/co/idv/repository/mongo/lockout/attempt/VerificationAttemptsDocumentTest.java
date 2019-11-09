package uk.co.idv.repository.mongo.lockout.attempt;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class VerificationAttemptsDocumentTest {

    @Test
    void shouldTestBean() {
        new BeanTester().testBean(VerificationAttemptsDocument.class);
    }

}
