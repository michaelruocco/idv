package uk.co.idv.repository.mongo.activity;

import org.junit.jupiter.api.Test;
import uk.co.idv.repository.mongo.beantest.DocumentBeanTester;

class MonetaryAmountDocumentTest {

    @Test
    void shouldTestBean() {
        new DocumentBeanTester().testBean(MonetaryAmountDocument.class);
    }

}
