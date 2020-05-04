package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import uk.co.idv.domain.entities.card.number.CardNumberMother;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.DefaultPinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.DefaultPinsentryParams.DefaultPinsentryParamsBuilder;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.IneligiblePinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry.PhysicalPinsentryBuilder;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;

import java.time.Duration;
import java.util.Collections;

public class PhysicalPinsentryMother {

    private static final PinsentryFunction FUNCTION = PinsentryFunction.RESPOND;

    public static PhysicalPinsentry ineligible() {
        return ineligible(FUNCTION);
    }

    public static PhysicalPinsentry ineligible(final PinsentryFunction function) {
        return PhysicalPinsentry.ineligibleBuilder()
                .params(new IneligiblePinsentryParams(function))
                .cardNumbers(Collections.emptyList())
                .eligibility(new NoEligibleCards())
                .build();
    }

    public static PhysicalPinsentry eligible() {
        return eligibleBuilder().build();
    }

    public static PhysicalPinsentryBuilder eligibleBuilder() {
        return PhysicalPinsentry.eligibleBuilder()
                .params(paramsBuilder().build())
                .cardNumbers(CardNumberMother.oneCredit())
                .eligibility(new Eligible())
                .results(new DefaultVerificationResults());
    }

    //TODO split params into separate object mother class
    public static DefaultPinsentryParams paramsWithMaxAttempts(int maxAttempts) {
        return paramsBuilder()
                .maxAttempts(maxAttempts)
                .build();
    }

    public static DefaultPinsentryParamsBuilder paramsBuilder() {
        return DefaultPinsentryParams.builder()
                .maxAttempts(1)
                .duration(Duration.ofMinutes(5))
                .function(FUNCTION);
    }

}
