package uk.co.mruoc.idv.mongo.lockout.dao.policy;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class LockoutPolicyDocumentTest {

    @Test
    void shouldTestBean() {
        new BeanTester().testBean(LockoutPolicyDocument.class);
    }

}
