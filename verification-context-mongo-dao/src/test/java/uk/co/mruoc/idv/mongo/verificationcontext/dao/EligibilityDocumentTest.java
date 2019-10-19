package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class EligibilityDocumentTest {

    @Test
    void shouldTestBean() {
        new BeanTester().testBean(EligibilityDocument.class);
    }

}
