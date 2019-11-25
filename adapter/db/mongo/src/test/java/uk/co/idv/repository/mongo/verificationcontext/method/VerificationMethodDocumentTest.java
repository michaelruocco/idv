package uk.co.idv.repository.mongo.verificationcontext.method;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.repository.mongo.beantest.DocumentBeanTester;
import uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocument;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationMethodDocumentTest {

    @Test
    void shouldTestBean() {
        new DocumentBeanTester().testBean(VerificationMethodDocument.class);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(VerificationMethodDocument.class)
                .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
                .withRedefinedSuperclass()
                .verify();
    }

    @Test
    void shouldReturnEligibleFromEligibility() {
        final EligibilityDocument eligibility = mock(EligibilityDocument.class);
        given(eligibility.isEligible()).willReturn(true);

        final VerificationMethodDocument document = new VerificationMethodDocument();
        document.setEligibility(eligibility);

        assertThat(document.isEligible()).isTrue();
    }

}
