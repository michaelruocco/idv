package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.cardnumber.CardNumber;
import uk.co.idv.domain.entities.cardnumber.CardNumberMother;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class PhysicalPinsentryEligibleTest {

    private final PinsentryFunction function = PinsentryFunction.RESPOND;
    private final Collection<CardNumber> cardNumbers = Collections.singleton(CardNumberMother.credit());

    private final PhysicalPinsentryEligible method = PhysicalPinsentryEligible.builder()
            .function(function)
            .cardNumbers(cardNumbers)
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
        assertThat(method.getFunction()).isEqualTo(function);
    }

    @Test
    void shouldReturnCardNumbers() {
        assertThat(method.getCardNumbers()).containsExactlyElementsOf(cardNumbers);
    }

    @Test
    void shouldAddResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(PhysicalPinsentry.NAME);

        final PhysicalPinsentryEligible methodWithResult = (PhysicalPinsentryEligible) method.addResult(result);

        assertThat(methodWithResult).isEqualToIgnoringGivenFields(method, "results", "cardNumbers");
        assertThat(methodWithResult.getCardNumbers()).containsExactlyElementsOf(cardNumbers);
        assertThat(methodWithResult.getResults()).containsExactly(result);
    }

}
