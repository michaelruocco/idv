package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParamsMother;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsMother;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class PhysicalPinsentryEligibleTest {

    private final PinsentryParams params = PinsentryParamsMother.eligible();
    private final Collection<CardNumber> cardNumbers = Collections.emptyList();

    private final PhysicalPinsentry method = new PhysicalPinsentryEligible(params, cardNumbers);

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(PhysicalPinsentry.NAME);
    }

    @Test
    void shouldReturnParams() {
        assertThat(method.getParams()).isEqualTo(params);
    }

    @Test
    void shouldBeEligible() {
        assertThat(method.getEligibility()).isEqualTo(new Eligible());
    }

    @Test
    void shouldReturnResults() {
        assertThat(method.getResults()).isEqualTo(VerificationResultsMother.empty());
    }

    @Test
    void shouldReturnFunction() {
        assertThat(method.getFunction()).isEqualTo(params.getFunction());
    }

    @Test
    void shouldReturnCardNumbers() {
        assertThat(method.getCardNumbers()).isEqualTo(cardNumbers);
    }

}
