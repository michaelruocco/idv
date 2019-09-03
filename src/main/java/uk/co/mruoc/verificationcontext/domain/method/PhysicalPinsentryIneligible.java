package uk.co.mruoc.verificationcontext.domain.method;

import lombok.Builder;

import java.util.Collections;

public class PhysicalPinsentryIneligible extends PhysicalPinsentry {

    @Builder
    public PhysicalPinsentryIneligible(final Ineligible ineligible, final PinsentryFunction function) {
        super(ineligible, function, Collections.emptyList());
    }

}
