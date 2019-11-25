package uk.co.idv.repository.mongo.verificationcontext;

import org.junit.jupiter.api.Test;
import uk.co.idv.repository.mongo.beantest.DocumentBeanTester;

class VerificationContextDocumentTest {

    @Test
    void shouldTestBean() {
        new DocumentBeanTester().testBean(VerificationContextDocument.class);
    }

}
