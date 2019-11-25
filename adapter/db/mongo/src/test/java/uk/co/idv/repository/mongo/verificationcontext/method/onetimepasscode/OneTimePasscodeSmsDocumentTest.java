package uk.co.idv.repository.mongo.verificationcontext.method.onetimepasscode;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.repository.mongo.beantest.DocumentBeanTester;

class OneTimePasscodeSmsDocumentTest {

    @Test
    void shouldTestBean() {
        new DocumentBeanTester().testBean(OneTimePasscodeSmsDocument.class);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(OneTimePasscodeSmsDocument.class)
                .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
                .withRedefinedSuperclass()
                .verify();
    }

}
