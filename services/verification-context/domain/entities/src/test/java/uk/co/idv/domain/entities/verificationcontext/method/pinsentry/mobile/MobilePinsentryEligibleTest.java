package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultFailed;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class MobilePinsentryEligibleTest {

    private final PinsentryFunction function = PinsentryFunction.IDENTIFY;

    private final MobilePinsentryEligible method = new MobilePinsentryEligible(function);

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(MobilePinsentry.NAME);
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
    void shouldReturnEmptyEligibilityReason() {
        assertThat(method.getEligibilityReason()).isEmpty();
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
    void shouldReturnFunction() {
        assertThat(method.getFunction()).isEqualTo(function);
    }

    @Test
    void shouldNotHaveResultsIfNoneAdded() {
        assertThat(method.hasResults()).isFalse();
    }

    @Test
    void shouldHaveResultIfResultAdded() {
        final VerificationResult result = new FakeVerificationResultSuccessful(MobilePinsentry.NAME);

        final VerificationMethod methodWithResult = new MobilePinsentryEligible(function, result);

        assertThat(methodWithResult.hasResults()).isTrue();
    }

    @Test
    void shouldBeCompleteIfHasSuccessfulResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(MobilePinsentry.NAME);

        final VerificationMethod completeMethod = new MobilePinsentryEligible(function, result);

        assertThat(completeMethod.isComplete()).isTrue();
    }

    @Test
    void shouldBeCompleteIfHasMaxAttemptsNumberOfFailedResults() {
        final int maxAttempts = 1;
        final VerificationResult result = new FakeVerificationResultFailed(MobilePinsentry.NAME);

        final VerificationMethod completeMethod = new MobilePinsentryEligible(function, new DefaultVerificationResults(result), maxAttempts, Duration.ZERO);

        assertThat(completeMethod.isComplete()).isTrue();
    }

    @Test
    void shouldNotBeCompleteIfHasFailedResultButHasAttemptsRemaining() {
        final int maxAttempts = 2;
        final VerificationResult result = new FakeVerificationResultFailed(MobilePinsentry.NAME);

        final VerificationMethod completeMethod = new MobilePinsentryEligible(function, new DefaultVerificationResults(result), maxAttempts, Duration.ZERO);

        assertThat(completeMethod.isComplete()).isFalse();
    }

    @Test
    void shouldBeSuccessfulIfHasSuccessfulResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(MobilePinsentry.NAME);

        final VerificationMethod successfulMethod = new MobilePinsentryEligible(function, result);

        assertThat(successfulMethod.isSuccessful()).isTrue();
    }

    @Test
    void shouldNotBeSuccessfulIfDoesNotHaveSuccessfulResult() {
        assertThat(method.isSuccessful()).isFalse();
    }

    @Test
    void shouldAddResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(MobilePinsentry.NAME);

        final VerificationMethod methodWithResult = method.addResult(result);

        assertThat(methodWithResult).isEqualToIgnoringGivenFields(method, "results");
        assertThat(methodWithResult.getResults()).containsExactly(result);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(MobilePinsentryEligible.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
