package uk.co.idv.repository.dynamo.identity;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import uk.co.idv.repository.dynamo.identity.alias.AliasMappingDocument;

class AliasMappingDocumentTest {

    @Test
    void shouldTestBean() {
        new BeanTester().testBean(AliasMappingDocument.class);
    }

}
