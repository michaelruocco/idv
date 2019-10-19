package uk.co.mruoc.idv.mongo.identity.dao;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class AliasDocumentTest {

    @Test
    void shouldTestBean() {
        new BeanTester().testBean(AliasDocument.class);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(AliasDocument.class)
                .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
                .verify();
    }

}
