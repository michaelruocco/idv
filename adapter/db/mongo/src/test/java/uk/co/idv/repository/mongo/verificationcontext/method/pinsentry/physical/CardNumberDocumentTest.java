package uk.co.idv.repository.mongo.verificationcontext.method.pinsentry.physical;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class CardNumberDocumentTest {

    @Test
    void shouldTestBean() {
        new BeanTester().testBean(CardNumberDocument.class);
    }

}
