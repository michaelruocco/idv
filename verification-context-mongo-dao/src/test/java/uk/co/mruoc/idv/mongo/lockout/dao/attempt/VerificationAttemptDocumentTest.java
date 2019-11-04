package uk.co.mruoc.idv.mongo.lockout.dao.attempt;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.mongo.beantest.CustomBeanTester;

class VerificationAttemptDocumentTest {

    @Test
    void shouldTestBean() {
        new CustomBeanTester().testBean(VerificationAttemptDocument.class);
    }

}
