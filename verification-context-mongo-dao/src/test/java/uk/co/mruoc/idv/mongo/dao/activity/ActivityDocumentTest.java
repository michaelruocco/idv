package uk.co.mruoc.idv.mongo.dao.activity;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class ActivityDocumentTest {

    @Test
    void shouldTestBean() {
        new BeanTester().testBean(ActivityDocument.class);
    }

}
