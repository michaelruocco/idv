package uk.co.idv.domain.entities.verificationcontext.method.pushnotification;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;
import uk.co.idv.domain.entities.verificationcontext.method.params.VerificationMethodParamsMother;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PushNotificationIneligibleTest {

    private final Ineligible ineligible = mock(Ineligible.class);

    private final PushNotification method = new PushNotificationIneligible(ineligible);

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(PushNotification.NAME);
    }

    @Test
    void shouldReturnParams() {
        assertThat(method.getParams()).isEqualTo(VerificationMethodParamsMother.ineligible());
    }

    @Test
    void shouldBeEligible() {
        assertThat(method.getEligibility()).isEqualTo(ineligible);
    }

    @Test
    void shouldReturnResults() {
        assertThat(method.getResults()).isEqualTo(VerificationResultsMother.empty());
    }

}
