package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import java.time.Duration;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class OneTimePasscodeEligibleTest {

    private final PasscodeSettings passcodeSettings = new DefaultPasscodeSettings();
    private final Collection<DeliveryMethod> deliveryMethods = DeliveryMethodMother.oneSms();

    private final OneTimePasscodeEligible method = new OneTimePasscodeEligible(passcodeSettings, deliveryMethods);

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(OneTimePasscode.NAME);
    }

    @Test
    void shouldReturnMaxAttempts() {
        assertThat(method.getMaxAttempts()).isEqualTo(1);
    }

    @Test
    void shouldReturnDuration() {
        assertThat(method.getDuration()).isEqualTo(Duration.ofMinutes(5));
    }

    @Test
    void shouldReturnEligibility() {
        assertThat(method.getEligibility()).isEqualTo(new Eligible());
    }

    @Test
    void shouldReturnPasscodeSettings() {
        assertThat(method.getPasscodeSettings()).isEqualTo(passcodeSettings);
    }

    @Test
    void shouldReturnMobileNumbers() {
        assertThat(method.getDeliveryMethods()).containsExactlyElementsOf(deliveryMethods);
    }

    @Test
    void shouldAddResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(OneTimePasscode.NAME);

        final OneTimePasscodeEligible methodWithResult = (OneTimePasscodeEligible) method.addResult(result);

        assertThat(methodWithResult).isEqualToIgnoringGivenFields(method, "results", "deliveryMethods");
        assertThat(methodWithResult.getDeliveryMethods()).containsExactlyElementsOf(deliveryMethods);
        assertThat(methodWithResult.getResults()).containsExactly(result);
    }

}
