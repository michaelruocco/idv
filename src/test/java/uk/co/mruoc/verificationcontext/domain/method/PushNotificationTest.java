package uk.co.mruoc.verificationcontext.domain.method;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class PushNotificationTest {

    private final Eligibility eligibility = new Eligible();
    private final VerificationMethod method = new PushNotification(eligibility);

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(VerificationMethod.Names.PUSH_NOTIFICATION);
    }

    @Test
    void shouldReturnDuration() {
        assertThat(method.getDuration()).isEqualTo(Duration.ofMinutes(5));
    }

    @Test
    void shouldReturnEligible() {
        assertThat(method.isEligible()).isEqualTo(eligibility.isEligible());
    }

    @Test
    void shouldReturnEligibility() {
        assertThat(method.getEligibility()).isEqualTo(eligibility);
    }

}
