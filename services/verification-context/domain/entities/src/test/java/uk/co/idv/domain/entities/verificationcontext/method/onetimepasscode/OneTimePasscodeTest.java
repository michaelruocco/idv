package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod.CannotAddResultToIneligibleMethodException;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode.DeliveryMethodNotFoundException;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.OneTimePasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.OneTimePasscodeParamsMother;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsMother;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OneTimePasscodeTest {

    private final OneTimePasscodeParams params = mock(OneTimePasscodeParams.class);
    private final Eligibility eligibility = mock(Eligibility.class);
    private final VerificationResults results = mock(VerificationResults.class);
    private final Collection<DeliveryMethod> deliveryMethods = Collections.emptyList();

    private final OneTimePasscode method = OneTimePasscode.builder()
            .params(params)
            .eligibility(eligibility)
            .results(results)
            .deliveryMethods(deliveryMethods)
            .build();

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(OneTimePasscode.NAME);
    }

    @Test
    void shouldReturnParams() {
        assertThat(method.getParams()).isEqualTo(params);
    }

    @Test
    void shouldReturnMaxAttempts() {
        final int expectedMaxAttempts = 5;
        given(params.getMaxAttempts()).willReturn(expectedMaxAttempts);

        final int maxAttempts = method.getMaxAttempts();

        assertThat(maxAttempts).isEqualTo(expectedMaxAttempts);
    }

    @Test
    void shouldReturnDuration() {
        final Duration expectedDuration = Duration.ofMinutes(5);
        given(params.getDuration()).willReturn(expectedDuration);

        final Duration duration = method.getDuration();

        assertThat(duration).isEqualTo(expectedDuration);
    }

    @Test
    void shouldReturnMaxDeliveries() {
        final int expectedMaxDeliveries = 3;
        given(params.getMaxDeliveries()).willReturn(expectedMaxDeliveries);

        final int maxDeliveries = method.getMaxDeliveries();

        assertThat(maxDeliveries).isEqualTo(expectedMaxDeliveries);
    }

    @Test
    void shouldReturnPasscodeLength() {
        final int expectedLength = 3;
        given(params.getPasscodeLength()).willReturn(expectedLength);

        final int length = method.getPasscodeLength();

        assertThat(length).isEqualTo(expectedLength);
    }

    @Test
    void shouldReturnEligibility() {
        assertThat(method.getEligibility()).isEqualTo(eligibility);
    }

    @Test
    void shouldReturnIsEligibleFromEligibility() {
        final boolean expectedEligible = true;
        given(eligibility.isEligible()).willReturn(expectedEligible);

        final boolean eligible = method.isEligible();

        assertThat(eligible).isEqualTo(expectedEligible);
    }

    @Test
    void shouldReturnEligibilityReasonFromEligibility() {
        final Optional<String> expectedReason = Optional.of("my-reason");
        given(eligibility.getReason()).willReturn(expectedReason);

        final Optional<String> reason = method.getEligibilityReason();

        assertThat(reason).isEqualTo(expectedReason);
    }

    @Test
    void shouldHaveOneTimePasscodeName() {
        assertThat(method.hasName(OneTimePasscode.NAME)).isTrue();
    }

    @Test
    void shouldNotHaveAnyOtherName() {
        assertThat(method.hasName("other")).isFalse();
    }

    @Test
    void shouldReturnDeliveryMethods() {
        assertThat(method.getDeliveryMethods()).containsExactlyElementsOf(deliveryMethods);
    }

    @Test
    void shouldReturnDeliveryMethodIfMethodWithIdFound() {
        final DeliveryMethod deliveryMethod = DeliveryMethodMother.sms();
        final OneTimePasscode methodWithDeliveryMethods = OneTimePasscode.eligibleBuilder()
                .deliveryMethods(Collections.singleton(deliveryMethod))
                .build();

        assertThat(methodWithDeliveryMethods.getDeliveryMethod(deliveryMethod.getId())).isEqualTo(deliveryMethod);
    }

    @Test
    void shouldThrowExceptionIfDeliveryMethodWithIdNotFound() {
        final UUID id = UUID.fromString("3b5faf02-c76c-470f-a56c-0172eab0da58");
        final DeliveryMethod deliveryMethod = DeliveryMethodMother.sms();
        final OneTimePasscode methodWithDeliveryMethods = OneTimePasscode.eligibleBuilder()
                .deliveryMethods(Collections.singleton(deliveryMethod))
                .build();

        final Throwable error = catchThrowable(() -> methodWithDeliveryMethods.getDeliveryMethod(id));

        assertThat(error).isInstanceOf(DeliveryMethodNotFoundException.class);
    }

    @Test
    void shouldReturnHasResultsIfResultsAreEmpty() {
        given(results.isEmpty()).willReturn(false);

        assertThat(method.hasResults()).isTrue();
    }

    @Test
    void shouldNotReturnHasResultsIfResultsAreEmpty() {
        given(results.isEmpty()).willReturn(true);

        assertThat(method.hasResults()).isFalse();
    }

    @Test
    void shouldNotBeCompleteIfMethodIsIneligible() {
        given(eligibility.isEligible()).willReturn(false);

        assertThat(method.isComplete()).isFalse();
    }

    @Test
    void shouldBeCompleteIfHasSuccessfulResult() {
        final VerificationMethod completeMethod = OneTimePasscodeMother.eligibleBuilder()
                .results(VerificationResultsMother.oneSuccessful(OneTimePasscode.NAME))
                .build();

        assertThat(completeMethod.isComplete()).isTrue();
    }

    @Test
    void shouldBeCompleteIfHasMaxAttemptsNumberOfFailedResults() {
        final VerificationMethod completeMethod = OneTimePasscode.eligibleBuilder()
                .params(OneTimePasscodeParamsMother.withMaxAttempts(1))
                .results(VerificationResultsMother.oneFailed(OneTimePasscode.NAME))
                .build();

        assertThat(completeMethod.isComplete()).isTrue();
    }

    @Test
    void shouldNotBeCompleteIfHasFailedResultButHasAttemptsRemaining() {
        final VerificationMethod incompleteMethod = OneTimePasscode.eligibleBuilder()
                .params(OneTimePasscodeParamsMother.withMaxAttempts(2))
                .results(VerificationResultsMother.oneFailed(OneTimePasscode.NAME))
                .build();

        assertThat(incompleteMethod.isComplete()).isFalse();
    }

    @Test
    void isSuccessfulShouldReturnResultsContainsSuccessful() {
        given(results.containsSuccessful()).willReturn(true);

        assertThat(method.isSuccessful()).isTrue();
    }

    @Test
    void shouldAddResult() {
        final VerificationResult result = VerificationResultsMother.successful(OneTimePasscode.NAME);
        final OneTimePasscode method = OneTimePasscode.eligibleBuilder()
                .params(OneTimePasscodeParamsMother.eligible())
                .build();

        method.addResult(result);

        assertThat(method.getResults()).containsExactly(result);
    }

    @Test
    void shouldNotAddResultIfIneligible() {
        final VerificationResult result = VerificationResultsMother.successful(OneTimePasscode.NAME);
        final OneTimePasscode emptyResultsMethod = OneTimePasscodeMother.ineligible();

        final Throwable error = catchThrowable(() -> emptyResultsMethod.addResult(result));

        assertThat(error)
                .isInstanceOf(CannotAddResultToIneligibleMethodException.class)
                .hasMessage(OneTimePasscode.NAME);
    }

}
