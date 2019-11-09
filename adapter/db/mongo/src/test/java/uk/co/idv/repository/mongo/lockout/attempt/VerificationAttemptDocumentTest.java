package uk.co.idv.repository.mongo.lockout.attempt;

import org.junit.jupiter.api.Test;
import uk.co.idv.repository.mongo.beantest.CustomBeanTester;

class VerificationAttemptDocumentTest {

    @Test
    void shouldTestBean() {
        new CustomBeanTester().testBean(VerificationAttemptDocument.class);
    }

}
