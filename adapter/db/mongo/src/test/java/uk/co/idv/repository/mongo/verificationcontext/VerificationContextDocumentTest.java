package uk.co.idv.repository.mongo.verificationcontext;

import org.junit.jupiter.api.Test;
import uk.co.idv.repository.mongo.beantest.CustomBeanTester;

class VerificationContextDocumentTest {

    @Test
    void shouldTestBean() {
        new CustomBeanTester().testBean(VerificationContextDocument.class);
    }

}
