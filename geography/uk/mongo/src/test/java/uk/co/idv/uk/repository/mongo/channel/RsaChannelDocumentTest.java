package uk.co.idv.uk.repository.mongo.channel;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class RsaChannelDocumentTest {

    @Test
    void shouldTestBean() {
        new BeanTester().testBean(RsaChannelDocument.class);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(RsaChannelDocument.class)
                .withRedefinedSuperclass()
                .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
                .verify();
    }

}
