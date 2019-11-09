package uk.co.idv.repository.mongo.verificationcontext.eligibility;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class EligibilityDocumentTest {

    @Test
    void shouldTestBean() {
        new BeanTester().testBean(EligibilityDocument.class);
    }

}
