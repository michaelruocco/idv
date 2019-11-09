package uk.co.idv.repository.mongo.lockout.policy;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class LockoutPolicyDocumentTest {

    @Test
    void shouldTestBean() {
        new BeanTester().testBean(LockoutPolicyDocument.class);
    }

}
