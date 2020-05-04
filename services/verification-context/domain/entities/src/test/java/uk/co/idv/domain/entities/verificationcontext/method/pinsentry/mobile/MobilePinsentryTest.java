package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod.CannotAddResultToIneligibleMethodException;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.DefaultPinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsMother;

import java.time.Duration;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MobilePinsentryTest {

    private final DefaultPinsentryParams params = mock(DefaultPinsentryParams.class);
    private final Eligibility eligibility = mock(Eligibility.class);
    private final VerificationResults results = mock(VerificationResults.class);

    private final MobilePinsentry method = MobilePinsentry.builder()
            .params(params)
            .eligibility(eligibility)
            .results(results)
            .build();

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo("mobile-pinsentry");
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
    void shouldReturnFunction() {
        final PinsentryFunction expectedFunction = PinsentryFunction.RESPOND;
        given(params.getFunction()).willReturn(expectedFunction);

        final PinsentryFunction function = method.getFunction();

        assertThat(function).isEqualTo(expectedFunction);
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
    void shouldHaveMobilePinsentryName() {
        assertThat(method.hasName(MobilePinsentry.NAME)).isTrue();
    }

    @Test
    void shouldNotHaveAnyOtherName() {
        assertThat(method.hasName("other")).isFalse();
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
        final VerificationMethod completeMethod = MobilePinsentry.eligibleBuilder()
                .params(MobilePinsentryMother.paramsWithMaxAttempts(2))
                .results(VerificationResultsMother.oneSuccessful(MobilePinsentry.NAME))
                .build();

        assertThat(completeMethod.isComplete()).isTrue();
    }

    @Test
    void shouldBeCompleteIfHasMaxAttemptsNumberOfFailedResults() {
        final VerificationMethod completeMethod = MobilePinsentryMother.eligibleBuilder()
                .params(MobilePinsentryMother.paramsWithMaxAttempts(1))
                .results(VerificationResultsMother.oneFailed(MobilePinsentry.NAME))
                .build();

        assertThat(completeMethod.isComplete()).isTrue();
    }

    @Test
    void shouldNotBeCompleteIfHasFailedResultButHasAttemptsRemaining() {
        final VerificationMethod incompleteMethod = MobilePinsentryMother.eligibleBuilder()
                .params(MobilePinsentryMother.paramsWithMaxAttempts(2))
                .results(VerificationResultsMother.oneFailed(MobilePinsentry.NAME))
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
        final VerificationResult result = VerificationResultsMother.successful(MobilePinsentry.NAME);
        final MobilePinsentry method = MobilePinsentryMother.eligible();

        method.addResult(result);

        assertThat(method.getResults()).containsExactly(result);
    }

    @Test
    void shouldNotAddResultIfIneligible() {
        final VerificationResult result = VerificationResultsMother.successful(MobilePinsentry.NAME);
        final MobilePinsentry emptyResultsMethod = MobilePinsentry.ineligibleBuilder().build();

        final Throwable error = catchThrowable(() -> emptyResultsMethod.addResult(result));

        assertThat(error)
                .isInstanceOf(CannotAddResultToIneligibleMethodException.class)
                .hasMessage(MobilePinsentry.NAME);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(MobilePinsentry.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
