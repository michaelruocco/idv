package uk.co.idv.repository.dynamo.identity;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class AliasMappingDocumentTest {

    @Test
    void shouldTestBean() {
        new BeanTester().testBean(AliasMappingDocument.class);
    }

}
