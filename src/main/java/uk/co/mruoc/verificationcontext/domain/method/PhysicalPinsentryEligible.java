package uk.co.mruoc.verificationcontext.domain.method;

import lombok.Builder;

import java.util.Collection;

public class PhysicalPinsentryEligible extends PhysicalPinsentry {

    @Builder
    public PhysicalPinsentryEligible(final PinsentryFunction function, final Collection<CardNumber> cardNumbers) {
        super(new Eligible(), function, cardNumbers);
    }

}
