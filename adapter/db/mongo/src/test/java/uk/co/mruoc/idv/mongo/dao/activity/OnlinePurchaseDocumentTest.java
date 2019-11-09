package uk.co.mruoc.idv.mongo.dao.activity;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.mongo.beantest.CustomBeanTester;

class OnlinePurchaseDocumentTest {

    @Test
    void shouldTestBean() {
        new CustomBeanTester().testBean(OnlinePurchaseDocument.class);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(OnlinePurchaseDocument.class)
                .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
                .withRedefinedSuperclass()
                .verify();
    }

}
