package uk.co.idv.domain.entities.verificationcontext.method.eligibility;

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
        assertThat(ineligible.getReason()).contains(REASON);
    }

}
