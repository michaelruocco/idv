package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeEligible.DeliveryMethodNotFoundException;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultFailed;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class OneTimePasscodeEligibleTest {

    private final PasscodeSettings PASSCODE_SETTINGS = new DefaultPasscodeSettings();
    private final DeliveryMethod DELIVERY_METHOD = DeliveryMethodMother.sms();
    private final Collection<DeliveryMethod> DELIVERY_METHODS = Collections.singleton(DELIVERY_METHOD);

    private final OneTimePasscodeEligible method = new OneTimePasscodeEligible(PASSCODE_SETTINGS, DELIVERY_METHODS);

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
    void shouldBeEligible() {
        assertThat(method.isEligible()).isTrue();
    }

    @Test
    void shouldReturnEmptyEligiblityReason() {
        assertThat(method.getEligibilityReason()).isEmpty();
    }

    @Test
    void shouldReturnPasscodeSettings() {
        assertThat(method.getPasscodeSettings()).isEqualTo(PASSCODE_SETTINGS);
    }

    @Test
    void shouldReturnDeliverMethods() {
        assertThat(method.getDeliveryMethods()).containsExactlyElementsOf(DELIVERY_METHODS);
    }

    @Test
    void shouldReturnPasscodeLength() {
        assertThat(method.getPasscodeLength()).isEqualTo(PASSCODE_SETTINGS.getLength());
    }

    @Test
    void shouldReturnMaxDeliveries() {
        assertThat(method.getMaxDeliveries()).isEqualTo(PASSCODE_SETTINGS.getMaxDeliveries());
    }

    @Test
    void shouldReturnDeliveryMethodIfMethodWithIdFound() {
        assertThat(method.getDeliveryMethod(DELIVERY_METHOD.getId())).isEqualTo(DELIVERY_METHOD);
    }

    @Test
    void shouldThrowExceptionIfDeliveryMethodWithIdNotFound() {
        final UUID id = UUID.fromString("3b5faf02-c76c-470f-a56c-0172eab0da58");

        final Throwable error = catchThrowable(() -> method.getDeliveryMethod(id));

        assertThat(error).isInstanceOf(DeliveryMethodNotFoundException.class);
    }

    @Test
    void shouldNotHaveResultsIfNoneAdded() {
        assertThat(method.hasResults()).isFalse();
    }

    @Test
    void shouldHaveResultIfResultAdded() {
        final VerificationResult result = new FakeVerificationResultSuccessful(OneTimePasscode.NAME);

        final VerificationMethod methodWithResult = new OneTimePasscodeEligible(PASSCODE_SETTINGS, DELIVERY_METHODS, result);

        assertThat(methodWithResult.hasResults()).isTrue();
    }

    @Test
    void shouldBeCompleteIfHasSuccessfulResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(OneTimePasscode.NAME);

        final VerificationMethod completeMethod = new OneTimePasscodeEligible(PASSCODE_SETTINGS, DELIVERY_METHODS, result);

        assertThat(completeMethod.isComplete()).isTrue();
    }

    @Test
    void shouldBeCompleteIfHasMaxAttemptsNumberOfFailedResults() {
        final int maxAttempts = 1;
        final VerificationResult result = new FakeVerificationResultFailed(OneTimePasscode.NAME);

        final VerificationMethod completeMethod = new OneTimePasscodeEligible(PASSCODE_SETTINGS, DELIVERY_METHODS, maxAttempts, Duration.ZERO, new DefaultVerificationResults(result));

        assertThat(completeMethod.isComplete()).isTrue();
    }

    @Test
    void shouldNotBeCompleteIfHasFailedResultButHasAttemptsRemaining() {
        final int maxAttempts = 2;
        final VerificationResult result = new FakeVerificationResultFailed(OneTimePasscode.NAME);

        final VerificationMethod incompleteMethod = new OneTimePasscodeEligible(PASSCODE_SETTINGS, DELIVERY_METHODS, maxAttempts, Duration.ZERO, new DefaultVerificationResults(result));

        assertThat(incompleteMethod.isComplete()).isFalse();
    }

    @Test
    void shouldBeSuccessfulIfHasSuccessfulResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(OneTimePasscode.NAME);

        final VerificationMethod successfulMethod = new OneTimePasscodeEligible(PASSCODE_SETTINGS, DELIVERY_METHODS, result);

        assertThat(successfulMethod.isSuccessful()).isTrue();
    }

    @Test
    void shouldNotBeSuccessfulIfDoesNotHaveSuccessfulResult() {
        assertThat(method.isSuccessful()).isFalse();
    }

    @Test
    void shouldAddResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(OneTimePasscode.NAME);

        final VerificationMethod methodWithResult = method.addResult(result);

        assertThat(methodWithResult).isEqualToIgnoringGivenFields(method, "results");
        assertThat(methodWithResult.getResults()).containsExactly(result);
    }

}
