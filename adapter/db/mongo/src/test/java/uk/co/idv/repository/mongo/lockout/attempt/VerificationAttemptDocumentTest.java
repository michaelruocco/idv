package uk.co.idv.repository.mongo.lockout.attempt;

import org.junit.jupiter.api.Test;
import uk.co.idv.repository.mongo.beantest.DocumentBeanTester;

class VerificationAttemptDocumentTest {

    @Test
    void shouldTestBean() {
        new DocumentBeanTester().testBean(VerificationAttemptDocument.class);
    }

}
