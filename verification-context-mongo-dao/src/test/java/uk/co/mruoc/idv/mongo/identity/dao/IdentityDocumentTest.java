package uk.co.mruoc.idv.mongo.identity.dao;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class IdentityDocumentTest {

    @Test
    void shouldTestBean() {
        new BeanTester().testBean(IdentityDocument.class);
    }

}
