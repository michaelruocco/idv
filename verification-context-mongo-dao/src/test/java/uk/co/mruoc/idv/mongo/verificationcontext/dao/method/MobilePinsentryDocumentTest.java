package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class MobilePinsentryDocumentTest {

    @Test
    void shouldTestBean() {
        new BeanTester().testBean(MobilePinsentryDocument.class);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(MobilePinsentryDocument.class)
                .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
                .withRedefinedSuperclass()
                .verify();
    }

}
