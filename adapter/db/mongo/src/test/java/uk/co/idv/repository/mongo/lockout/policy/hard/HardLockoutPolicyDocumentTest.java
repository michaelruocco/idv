package uk.co.idv.repository.mongo.lockout.policy.hard;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class HardLockoutPolicyDocumentTest {

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(HardLockoutPolicyDocument.class)
                .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
                .withRedefinedSuperclass()
                .verify();
    }

}
