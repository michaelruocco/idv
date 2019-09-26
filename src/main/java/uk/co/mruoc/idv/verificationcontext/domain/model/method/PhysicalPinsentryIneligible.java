package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import lombok.Builder;

public class PhysicalPinsentryIneligible extends AbstractVerificationMethodIneligible implements PhysicalPinsentry {

    private final PinsentryFunction function;

    @Builder
    public PhysicalPinsentryIneligible(final Ineligible ineligible, final PinsentryFunction function) {
        super(NAME, ineligible);
        this.function = function;
    }

    @Override
    public PinsentryFunction getFunction() {
        return function;
    }

}
