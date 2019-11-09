package uk.co.idv.repository.mongo.activity;

import org.junit.jupiter.api.Test;
import uk.co.idv.repository.mongo.beantest.CustomBeanTester;

class MonetaryAmountDocumentTest {

    @Test
    void shouldTestBean() {
        new CustomBeanTester().testBean(MonetaryAmountDocument.class);
    }

}
