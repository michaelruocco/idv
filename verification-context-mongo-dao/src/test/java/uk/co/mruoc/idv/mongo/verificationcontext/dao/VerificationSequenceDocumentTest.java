package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class VerificationSequenceDocumentTest {

    @Test
    void shouldTestBean() {
        new BeanTester().testBean(VerificationSequenceDocument.class);
    }

}
