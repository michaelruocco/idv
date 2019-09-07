package uk.co.mruoc.verificationcontext.domain.method;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationMethodTest {

    @Test
    void shouldReturnHasNameTrueIfNameMatches() {
        final VerificationMethod method = new FakeVerificationMethod();

        final boolean hasName = method.hasName(method.getName());

        assertThat(hasName).isTrue();
    }

    @Test
    void shouldReturnHasNameFalseIfNameDoesNotMatch() {
        final VerificationMethod method = new FakeVerificationMethod();

        final boolean hasName = method.hasName("other-name");

        assertThat(hasName).isFalse();
    }

    @Test
    void shouldReturnIsEligibleTrueIfEligible() {
        final VerificationMethod method = new FakeVerificationMethod();

        final boolean eligible = method.isEligible();

        assertThat(eligible).isTrue();
    }

    @Test
    void shouldReturnIsEligibleTrueIfIneligible() {
        final VerificationMethod method = new FakeVerificationMethod(new Ineligible("reason"));

        final boolean eligible = method.isEligible();

        assertThat(eligible).isFalse();
    }

}
