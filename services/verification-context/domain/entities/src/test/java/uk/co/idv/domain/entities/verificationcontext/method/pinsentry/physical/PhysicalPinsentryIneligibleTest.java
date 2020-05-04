package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParamsMother;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsMother;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PhysicalPinsentryIneligibleTest {

    private static final PinsentryFunction FUNCTION = PinsentryFunction.RESPOND;

    private final Ineligible ineligible = mock(Ineligible.class);
    private final Collection<CardNumber> cardNumbers = Collections.emptyList();

    private final PhysicalPinsentry method = new PhysicalPinsentryIneligible(FUNCTION, ineligible, cardNumbers);

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(PhysicalPinsentry.NAME);
    }

    @Test
    void shouldReturnParams() {
        assertThat(method.getParams()).isEqualTo(PinsentryParamsMother.ineligible(FUNCTION));
    }

    @Test
    void shouldBeIneligible() {
        assertThat(method.getEligibility()).isEqualTo(ineligible);
    }

    @Test
    void shouldReturnResults() {
        assertThat(method.getResults()).isEqualTo(VerificationResultsMother.empty());
    }

    @Test
    void shouldReturnFunction() {
        assertThat(method.getFunction()).isEqualTo(FUNCTION);
    }

}
