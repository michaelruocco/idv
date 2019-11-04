package uk.co.mruoc.idv.mongo.dao.activity;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.mongo.beantest.CustomBeanTester;

class MonetaryAmountDocumentTest {

    @Test
    void shouldTestBean() {
        new CustomBeanTester().testBean(MonetaryAmountDocument.class);
    }

}
