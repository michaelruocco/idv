package uk.co.mruoc.idv.mongo.lockout.dao;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.mongo.CustomBeanTester;

class VerificationAttemptDocumentTest {

    @Test
    void shouldTestBean() {
        new CustomBeanTester().testBean(VerificationAttemptDocument.class);
    }

}
