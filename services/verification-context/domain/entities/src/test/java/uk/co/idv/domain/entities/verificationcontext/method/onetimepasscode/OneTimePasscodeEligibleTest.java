package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeEligible.DeliveryMethodNotFoundException;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class OneTimePasscodeEligibleTest {

    private final PasscodeSettings passcodeSettings = new DefaultPasscodeSettings();
    private final DeliveryMethod deliveryMethod = DeliveryMethodMother.sms();
    private final Collection<DeliveryMethod> deliveryMethods = Collections.singleton(deliveryMethod);

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
    void shouldReturnDeliverMethods() {
        assertThat(method.getDeliveryMethods()).containsExactlyElementsOf(deliveryMethods);
    }

    @Test
    void shouldReturnPasscodeLength() {
        assertThat(method.getPasscodeLength()).isEqualTo(passcodeSettings.getLength());
    }

    @Test
    void shouldReturnMaxDeliveries() {
        assertThat(method.getMaxDeliveries()).isEqualTo(passcodeSettings.getMaxDeliveries());
    }

    @Test
    void shouldReturnDeliveryMethodIfMethodWithIdFound() {
        assertThat(method.getDeliveryMethod(deliveryMethod.getId())).isEqualTo(deliveryMethod);
    }

    @Test
    void shouldThrowExceptionIfDeliveryMethodWithIdNotFound() {
        final UUID id = UUID.fromString("3b5faf02-c76c-470f-a56c-0172eab0da58");

        final Throwable error = catchThrowable(() -> method.getDeliveryMethod(id));

        assertThat(error).isInstanceOf(DeliveryMethodNotFoundException.class);
    }

    @Test
    void shouldAddResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(OneTimePasscode.NAME);

        final OneTimePasscodeEligible methodWithResult = (OneTimePasscodeEligible) method.addResult(result);

        assertThat(methodWithResult).isEqualToIgnoringGivenFields(method, "results");
        assertThat(methodWithResult.getResults()).containsExactly(result);
    }

}
