package uk.co.mruoc.idv.verificationcontext.domain.model.method;

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
