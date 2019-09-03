package uk.co.mruoc.verificationcontext.domain.method;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EligibleTest {

    private final Eligibility eligible = new Eligible();

    @Test
    void shouldBeEligible() {
        assertThat(eligible.isEligible()).isTrue();
    }

    @Test
    void reasonShouldBeEmpty() {
        assertThat(eligible.reason()).isEmpty();
    }

}
