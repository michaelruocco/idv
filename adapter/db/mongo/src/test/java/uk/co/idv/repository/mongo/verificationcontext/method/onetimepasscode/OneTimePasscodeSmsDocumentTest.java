package uk.co.idv.repository.mongo.verificationcontext.method.onetimepasscode;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.repository.mongo.beantest.CustomBeanTester;

class OneTimePasscodeSmsDocumentTest {

    @Test
    void shouldTestBean() {
        new CustomBeanTester().testBean(OneTimePasscodeSmsDocument.class);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(OneTimePasscodeSmsDocument.class)
                .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
                .withRedefinedSuperclass()
                .verify();
    }

}
