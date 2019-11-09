package uk.co.idv.repository.mongo.lockout.policy;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.repository.mongo.lockout.policy.maxattempts.MaxAttemptsLockoutPolicyDocument;

class MaxAttemptsLockoutPolicyDocumentTest {

    @Test
    void shouldBeEqualIfAllValuesAreTheSame() {
        EqualsVerifier.forClass(MaxAttemptsLockoutPolicyDocument.class)
                .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
                .withRedefinedSuperclass()
                .verify();
    }

}
