package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.Eligibility;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.Eligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.FakeIneligible;

import static org.assertj.core.api.Assertions.assertThat;

class EligibilityConverterTest {

    private final EligibilityConverter converter = new EligibilityConverter();

    @Test
    void shouldConvertToIneligible() {
        final EligibilityDocument document = EligibilityDocumentMother.ineligible();

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
        final EligibilityDocument document = EligibilityDocumentMother.ineligible();

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
        final EligibilityDocument document = EligibilityDocumentMother.eligible();

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
