package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import lombok.Builder;
import uk.co.idv.domain.entities.verificationcontext.method.AbstractVerificationMethodIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;

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
