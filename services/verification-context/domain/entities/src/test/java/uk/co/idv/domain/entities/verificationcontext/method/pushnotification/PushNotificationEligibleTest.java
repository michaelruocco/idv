package uk.co.idv.domain.entities.verificationcontext.method.pushnotification;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsMother;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class PushNotificationEligibleTest {

    private final VerificationMethod method = PushNotificationMother.eligible();

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(PushNotification.NAME);
    }

    @Test
    void shouldReturnMaxAttempts() {
        assertThat(method.getMaxAttempts()).isEqualTo(5);
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
    void shouldHaveEmptyEligibilityReason() {
        assertThat(method.getEligibilityReason()).isEmpty();
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
    void shouldNotHaveResultsIfNoneAdded() {
        assertThat(method.hasResults()).isFalse();
    }

    @Test
    void shouldHaveResultIfResultAdded() {
        final VerificationMethod methodWithResult = PushNotificationMother.eligibleBuilder()
                .results(VerificationResultsMother.oneSuccessful(PushNotification.NAME))
                .build();

        assertThat(methodWithResult.hasResults()).isTrue();
    }

    @Test
    void shouldBeCompleteIfHasSuccessfulResult() {
        final VerificationMethod completeMethod = PushNotificationMother.eligibleBuilder()
                .results(VerificationResultsMother.oneSuccessful(PushNotification.NAME))
                .build();

        assertThat(completeMethod.isComplete()).isTrue();
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
    void shouldNotBeSuccessfulIfDoesNotHaveSuccessfulResult() {
        assertThat(method.isSuccessful()).isFalse();
    }

    @Test
    void shouldAddResult() {
        final VerificationResult result = VerificationResultsMother.successful(PushNotification.NAME);

        final VerificationMethod methodWithResult = method.addResult(result);

        assertThat(methodWithResult).isEqualToIgnoringGivenFields(method, "results");
        assertThat(methodWithResult.getResults()).containsExactly(result);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(PushNotificationEligible.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
