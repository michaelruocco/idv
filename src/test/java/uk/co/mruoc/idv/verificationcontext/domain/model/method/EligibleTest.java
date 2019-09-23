package uk.co.mruoc.idv.verificationcontext.domain.model.method;

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
        assertThat(eligible.getReason()).isEmpty();
    }

}
