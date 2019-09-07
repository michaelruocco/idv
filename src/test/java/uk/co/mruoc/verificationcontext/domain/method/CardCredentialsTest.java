package uk.co.mruoc.verificationcontext.domain.method;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class CardCredentialsTest {

    private final Eligibility eligibility = new Eligible();

    private final VerificationMethod method = new CardCredentials(eligibility);

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo("card-credentials");
    }

    @Test
    void shouldReturnDuration() {
        assertThat(method.getDuration()).isEqualTo(Duration.ofMinutes(5));
    }

    @Test
    void shouldReturnEligibility() {
        assertThat(method.getEligibility()).isEqualTo(eligibility);
    }

}
