package uk.co.idv.repository.mongo.activity;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.repository.mongo.beantest.DocumentBeanTester;

class OnlinePurchaseDocumentTest {

    @Test
    void shouldTestBean() {
        new DocumentBeanTester().testBean(OnlinePurchaseDocument.class);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(OnlinePurchaseDocument.class)
                .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
                .withRedefinedSuperclass()
                .verify();
    }

}
