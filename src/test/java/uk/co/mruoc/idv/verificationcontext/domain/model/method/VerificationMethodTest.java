package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationMethodTest {

    @Test
    void shouldReturnHasNameTrueIfNameMatches() {
        final VerificationMethod method = new FakeVerificationMethodEligible();

        final boolean hasName = method.hasName(method.getName());

        assertThat(hasName).isTrue();
    }

    @Test
    void shouldReturnHasNameFalseIfNameDoesNotMatch() {
        final VerificationMethod method = new FakeVerificationMethodEligible();

        final boolean hasName = method.hasName("other-name");

        assertThat(hasName).isFalse();
    }

    @Test
    void shouldReturnIsEligibleTrueIfEligible() {
        final VerificationMethod method = new FakeVerificationMethodEligible();

        final boolean eligible = method.isEligible();

        assertThat(eligible).isTrue();
    }

    @Test
    void shouldReturnIsEligibleFalseIfIneligible() {
        final VerificationMethod method = new FakeVerificationMethodIneligible();

        final boolean eligible = method.isEligible();

        assertThat(eligible).isFalse();
    }

    @Test
    void shouldReturnIsEmptyEligibilityReasonIfEligible() {
        final VerificationMethod method = new FakeVerificationMethodEligible();

        final Optional<String> reason = method.getEligibilityReason();

        assertThat(reason).isEmpty();
    }

    @Test
    void shouldReturnIsIneligibilityReasonIfIneligible() {
        final VerificationMethod method = new FakeVerificationMethodIneligible();

        final Optional<String> reason = method.getEligibilityReason();

        assertThat(reason).contains("fake ineligible reason");
    }

}
