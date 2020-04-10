package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile;

import uk.co.idv.domain.entities.verificationcontext.method.AbstractVerificationMethodIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.NoMobileApplication;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;

public class MobilePinsentryIneligible extends AbstractVerificationMethodIneligible implements MobilePinsentry {

    private static final String NAME = "mobile-pinsentry";

    private final PinsentryFunction function;

    public MobilePinsentryIneligible(final PinsentryFunction function) {
        super(NAME, new NoMobileApplication());
        this.function = function;
    }

    @Override
    public PinsentryFunction getFunction() {
        return function;
    }

}
