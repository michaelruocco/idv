package uk.co.idv.domain.entities.verificationcontext.method.pushnotification;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod.CannotAddResultToIneligibleMethodException;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsMother;

import java.time.Duration;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PushNotificationTest {

    private final VerificationMethodParams params = mock(VerificationMethodParams.class);
    private final Eligibility eligibility = mock(Eligibility.class);
    private final VerificationResults results = mock(VerificationResults.class);

    private final PushNotification method = PushNotification.builder()
            .params(params)
            .eligibility(eligibility)
            .results(results)
            .build();

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(PushNotification.NAME);
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
    void shouldReturnEligibility() {
        assertThat(method.getEligibility()).isEqualTo(eligibility);
    }

    @Test
    void shouldBeEligible() {
        final boolean expectedEligible = true;
        given(eligibility.isEligible()).willReturn(expectedEligible);

        final boolean eligible = method.isEligible();

        assertThat(eligible).isEqualTo(expectedEligible);
    }

    @Test
    void shouldHaveEmptyEligibilityReason() {
        final Optional<String> expectedReason = Optional.of("my-reason");
        given(eligibility.getReason()).willReturn(expectedReason);

        final Optional<String> reason = method.getEligibilityReason();

        assertThat(reason).isEqualTo(expectedReason);
    }

    @Test
    void shouldHavePushNotificationName() {
        assertThat(method.hasName(PushNotification.NAME)).isTrue();
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
    void shouldBeCompleteIfHasMaxAttemptsNumberOfFailedResults() {
        final VerificationMethod completeMethod = PushNotificationMother.eligibleBuilder()
                .params(PushNotificationMother.paramsWithMaxAttempts(1))
                .results(VerificationResultsMother.oneFailed(PushNotification.NAME))
                .build();

        assertThat(completeMethod.isComplete()).isTrue();
    }

    @Test
    void shouldNotBeCompleteIfMethodIsIneligible() {
        given(eligibility.isEligible()).willReturn(false);

        assertThat(method.isComplete()).isFalse();
    }

    @Test
    void shouldNotBeCompleteIfHasFailedResultButHasAttemptsRemaining() {
        final VerificationMethod incompleteMethod = PushNotificationMother.eligibleBuilder()
                .params(PushNotificationMother.paramsWithMaxAttempts(2))
                .results(VerificationResultsMother.oneFailed(PushNotification.NAME))
                .build();

        assertThat(incompleteMethod.isComplete()).isFalse();
    }

    @Test
    void shouldBeSuccessfulIfHasSuccessfulResult() {
        final VerificationMethod successfulMethod = PushNotificationMother.eligibleBuilder()
                .results(VerificationResultsMother.oneSuccessful(PushNotification.NAME))
                .build();

        assertThat(successfulMethod.isSuccessful()).isTrue();
    }

    @Test
    void shouldAddResult() {
        final VerificationResult result = VerificationResultsMother.successful(PushNotification.NAME);
        final PushNotification emptyResultsMethod = PushNotificationMother.eligible();

        final VerificationMethod methodWithResult = emptyResultsMethod.addResult(result);

        assertThat(methodWithResult).isEqualToIgnoringGivenFields(emptyResultsMethod, "results");
        assertThat(methodWithResult.getResults()).containsExactly(result);
    }

    @Test
    void shouldNotAddResultIfIneligible() {
        final VerificationResult result = VerificationResultsMother.successful(PushNotification.NAME);
        final PushNotification emptyResultsMethod = PushNotificationMother.ineligible();

        final Throwable error = catchThrowable(() -> emptyResultsMethod.addResult(result));

        assertThat(error)
                .isInstanceOf(CannotAddResultToIneligibleMethodException.class)
                .hasMessage(PushNotification.NAME);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(PushNotification.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
