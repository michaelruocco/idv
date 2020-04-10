package uk.co.idv.domain.entities.verificationcontext.method.pushnotification;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.NoMobileApplication;

import static org.assertj.core.api.Assertions.assertThat;

class PushNotificationIneligibleTest {

    private final VerificationMethod method = new PushNotificationIneligible();

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(PushNotification.NAME);
    }

    @Test
    void shouldReturnEligibility() {
        assertThat(method.getEligibility()).isEqualTo(new NoMobileApplication());
    }

}
