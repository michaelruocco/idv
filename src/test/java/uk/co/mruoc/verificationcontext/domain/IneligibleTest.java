package uk.co.mruoc.verificationcontext.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IneligibleTest {

    private static final String REASON = "User does not have mobile application";

    private final Eligibility ineligible = new Ineligible(REASON);

    @Test
    void shouldNotBeEligible() {
        assertThat(ineligible.isEligible()).isFalse();
    }

    @Test
    void shouldReturnReason() {
        assertThat(ineligible.reason()).contains(REASON);
    }

}
