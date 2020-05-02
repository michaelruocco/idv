package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.card.number.CardNumberMother;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultFailed;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class PhysicalPinsentryEligibleTest {

    private static final PinsentryFunction FUNCTION = PinsentryFunction.RESPOND;
    private static final Collection<CardNumber> CARD_NUMBERS = Collections.singleton(CardNumberMother.credit());

    private final PhysicalPinsentryEligible method = PhysicalPinsentryEligible.builder()
            .function(FUNCTION)
            .cardNumbers(CARD_NUMBERS)
            .build();

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo("physical-pinsentry");
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
    void shouldReturnFunction() {
        assertThat(method.getFunction()).isEqualTo(FUNCTION);
    }

    @Test
    void shouldReturnCardNumbers() {
        assertThat(method.getCardNumbers()).containsExactlyElementsOf(CARD_NUMBERS);
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
        assertThat(method.hasName(PhysicalPinsentry.NAME)).isTrue();
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
        final VerificationResult result = new FakeVerificationResultSuccessful(PhysicalPinsentry.NAME);

        final VerificationMethod methodWithResult = new PhysicalPinsentryEligible(FUNCTION, CARD_NUMBERS, result);

        assertThat(methodWithResult.hasResults()).isTrue();
    }

    @Test
    void shouldBeCompleteIfHasSuccessfulResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(PhysicalPinsentry.NAME);

        final VerificationMethod completeMethod = new PhysicalPinsentryEligible(FUNCTION, CARD_NUMBERS, result);

        assertThat(completeMethod.isComplete()).isTrue();
    }

    @Test
    void shouldBeCompleteIfHasMaxAttemptsNumberOfFailedResults() {
        final int maxAttempts = 1;
        final VerificationResult result = new FakeVerificationResultFailed(PhysicalPinsentry.NAME);

        final VerificationMethod completeMethod = new PhysicalPinsentryEligible(new DefaultVerificationResults(result), maxAttempts, Duration.ZERO, FUNCTION, CARD_NUMBERS);

        assertThat(completeMethod.isComplete()).isTrue();
    }

    @Test
    void shouldNotBeCompleteIfHasFailedResultButHasAttemptsRemaining() {
        final int maxAttempts = 2;
        final VerificationResult result = new FakeVerificationResultFailed(PhysicalPinsentry.NAME);

        final VerificationMethod incompleteMethod = new PhysicalPinsentryEligible(new DefaultVerificationResults(result), maxAttempts, Duration.ZERO, FUNCTION, CARD_NUMBERS);

        assertThat(incompleteMethod.isComplete()).isFalse();
    }

    @Test
    void shouldBeSuccessfulIfHasSuccessfulResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(PhysicalPinsentry.NAME);

        final VerificationMethod successfulMethod = new PhysicalPinsentryEligible(FUNCTION, CARD_NUMBERS, result);

        assertThat(successfulMethod.isSuccessful()).isTrue();
    }

    @Test
    void shouldNotBeSuccessfulIfDoesNotHaveSuccessfulResult() {
        assertThat(method.isSuccessful()).isFalse();
    }

    @Test
    void shouldAddResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(PhysicalPinsentry.NAME);

        final PhysicalPinsentryEligible methodWithResult = (PhysicalPinsentryEligible) method.addResult(result);

        assertThat(methodWithResult).isEqualToIgnoringGivenFields(method, "results", "cardNumbers");
        assertThat(methodWithResult.getCardNumbers()).containsExactlyElementsOf(CARD_NUMBERS);
        assertThat(methodWithResult.getResults()).containsExactly(result);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(PhysicalPinsentryEligible.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
