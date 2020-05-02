package uk.co.idv.domain.entities.verificationcontext.method.pushnotification;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultFailed;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class PushNotificationEligibleTest {

    private final VerificationMethod method = new PushNotificationEligible();

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
        final VerificationResult result = new FakeVerificationResultSuccessful(PushNotification.NAME);

        final VerificationMethod methodWithResult = new PushNotificationEligible(result);

        assertThat(methodWithResult.hasResults()).isTrue();
    }

    @Test
    void shouldBeCompleteIfHasSuccessfulResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(PushNotification.NAME);

        final VerificationMethod completeMethod = new PushNotificationEligible(result);

        assertThat(completeMethod.isComplete()).isTrue();
    }

    @Test
    void shouldBeCompleteIfHasMaxAttemptsNumberOfFailedResults() {
        final int maxAttempts = 1;
        final VerificationResult result = new FakeVerificationResultFailed(PushNotification.NAME);

        final VerificationMethod completeMethod = new PushNotificationEligible(maxAttempts, Duration.ZERO, new DefaultVerificationResults(result));

        assertThat(completeMethod.isComplete()).isTrue();
    }

    @Test
    void shouldNotBeCompleteIfHasFailedResultButHasAttemptsRemaining() {
        final int maxAttempts = 2;
        final VerificationResult result = new FakeVerificationResultFailed(PushNotification.NAME);

        final VerificationMethod completeMethod = new PushNotificationEligible(maxAttempts, Duration.ZERO, new DefaultVerificationResults(result));

        assertThat(completeMethod.isComplete()).isFalse();
    }

    @Test
    void shouldBeSuccessfulIfHasSuccessfulResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(PushNotification.NAME);

        final VerificationMethod successfulMethod = new PushNotificationEligible(result);

        assertThat(successfulMethod.isSuccessful()).isTrue();
    }

    @Test
    void shouldNotBeSuccessfulIfDoesNotHaveSuccessfulResult() {
        assertThat(method.isSuccessful()).isFalse();
    }

    @Test
    void shouldAddResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(PushNotification.NAME);

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
