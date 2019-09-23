package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import lombok.Builder;

import java.time.Duration;
import java.util.Collection;

public class PhysicalPinsentryEligible extends PhysicalPinsentry {

    @Builder
    public PhysicalPinsentryEligible(final PinsentryFunction function, final Collection<CardNumber> cardNumbers) {
        super(new Eligible(), Duration.ofMinutes(5), function, cardNumbers);
    }

}
