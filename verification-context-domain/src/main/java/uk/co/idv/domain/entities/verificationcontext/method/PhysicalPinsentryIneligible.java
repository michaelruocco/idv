package uk.co.idv.domain.entities.verificationcontext.method;

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
