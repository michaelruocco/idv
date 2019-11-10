package uk.co.idv.repository.mongo.verificationcontext.eligibility;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.FakeIneligible;

import static org.assertj.core.api.Assertions.assertThat;

class EligibilityDocumentConverterTest {

    private final EligibilityDocumentConverter converter = new EligibilityDocumentConverter();

    @Test
    void shouldConvertToIneligible() {
        final EligibilityDocument document = uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocumentMother.ineligible();

        final Eligibility ineligible = converter.toEligibility(document);

        assertThat(ineligible.isEligible()).isFalse();
    }

    @Test
    void shouldConvertToIneligibleDocument() {
        final Eligibility ineligible = new FakeIneligible();

        final EligibilityDocument document = converter.toDocument(ineligible);

        assertThat(document.isEligible()).isFalse();
    }

    @Test
    void shouldConvertReasonToIneligible() {
        final EligibilityDocument document = uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocumentMother.ineligible();

        final Eligibility ineligible = converter.toEligibility(document);

        assertThat(ineligible.getReason()).contains(document.getReason());
    }

    @Test
    void shouldConvertReasonToDocument() {
        final Eligibility ineligible = new FakeIneligible();

        final EligibilityDocument document = converter.toDocument(ineligible);

        assertThat(ineligible.getReason().isPresent()).isTrue();
        assertThat(document.getReason()).isEqualTo(ineligible.getReason().get());
    }

    @Test
    void shouldConvertToEligible() {
        final EligibilityDocument document = uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocumentMother.eligible();

        final Eligibility eligibility = converter.toEligibility(document);

        assertThat(eligibility.isEligible()).isTrue();
    }

    @Test
    void shouldConvertToEligibleDocument() {
        final Eligibility eligible = new Eligible();

        final EligibilityDocument document = converter.toDocument(eligible);

        assertThat(document.isEligible()).isTrue();
    }

    @Test
    void shouldConvertEmptyReasonToEligible() {
        final EligibilityDocument document = EligibilityDocumentMother.eligible();

        final Eligibility eligibility = converter.toEligibility(document);

        assertThat(eligibility.getReason()).isEmpty();
    }

    @Test
    void shouldConvertNullReasonToDocument() {
        final Eligibility ineligible = new Eligible();

        final EligibilityDocument document = converter.toDocument(ineligible);

        assertThat(document.getReason()).isNull();
    }

}
