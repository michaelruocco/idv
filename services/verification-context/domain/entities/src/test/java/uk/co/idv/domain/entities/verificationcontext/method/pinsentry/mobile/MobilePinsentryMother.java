package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile;

import uk.co.idv.domain.entities.verificationcontext.method.eligibility.NoMobileApplication;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryEligible.MobilePinsentryEligibleBuilder;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParamsMother;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;

public class MobilePinsentryMother {

    private static final PinsentryFunction FUNCTION = PinsentryFunction.RESPOND;

    public static MobilePinsentry ineligible() {
        return ineligible(FUNCTION);
    }

    public static MobilePinsentry ineligible(final PinsentryFunction function) {
        return new MobilePinsentryIneligible(function, new NoMobileApplication());
    }

    public static MobilePinsentry eligible() {
        return eligibleBuilder().build();
    }

    public static MobilePinsentryEligibleBuilder eligibleBuilder() {
        return MobilePinsentryEligible.builder()
                .params(PinsentryParamsMother.eligible())
                .results(new DefaultVerificationResults());
    }

}
