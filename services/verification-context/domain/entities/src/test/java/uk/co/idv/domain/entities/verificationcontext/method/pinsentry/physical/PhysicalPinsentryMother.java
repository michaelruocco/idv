package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import uk.co.idv.domain.entities.card.number.CardNumberMother;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParamsMother;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryEligible.PhysicalPinsentryEligibleBuilder;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsMother;

import java.util.Collections;

public class PhysicalPinsentryMother {

    private static final PinsentryFunction FUNCTION = PinsentryFunction.RESPOND;

    public static PhysicalPinsentry ineligible() {
        return ineligible(FUNCTION);
    }

    public static PhysicalPinsentry ineligible(final PinsentryFunction function) {
        return new PhysicalPinsentryIneligible(function, new NoEligibleCards(), Collections.emptyList());
    }

    public static PhysicalPinsentry eligible() {
        return eligibleBuilder().build();
    }

    public static PhysicalPinsentryEligibleBuilder eligibleBuilder() {
        return PhysicalPinsentryEligible.builder()
                .params(PinsentryParamsMother.eligible())
                .cardNumbers(CardNumberMother.oneCredit())
                .results(VerificationResultsMother.empty());
    }

}
