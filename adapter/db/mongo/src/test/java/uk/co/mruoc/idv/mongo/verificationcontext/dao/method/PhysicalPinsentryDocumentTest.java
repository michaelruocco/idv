package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.mongo.beantest.CustomBeanTester;

class PhysicalPinsentryDocumentTest {

    @Test
    void shouldTestBean() {
        new CustomBeanTester().testBean(PhysicalPinsentryDocument.class);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(PhysicalPinsentryDocument.class)
                .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
                .withRedefinedSuperclass()
                .verify();
    }

}
