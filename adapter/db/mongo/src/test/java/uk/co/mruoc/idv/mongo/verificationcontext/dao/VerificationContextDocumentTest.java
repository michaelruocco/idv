package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.mongo.beantest.CustomBeanTester;

class VerificationContextDocumentTest {

    @Test
    void shouldTestBean() {
        new CustomBeanTester().testBean(VerificationContextDocument.class);
    }

}
