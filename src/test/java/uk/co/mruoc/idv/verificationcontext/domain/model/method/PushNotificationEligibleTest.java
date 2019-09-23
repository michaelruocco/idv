package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class PushNotificationEligibleTest {

    private final VerificationMethod method = new PushNotificationEligible();

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo("push-notification");
    }

    @Test
    void shouldReturnDuration() {
        assertThat(method.getDuration()).isEqualTo(Duration.ofMinutes(5));
    }

    @Test
    void shouldReturnEligibility() {
        assertThat(method.getEligibility()).isEqualTo(new Eligible());
    }

}
