package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import lombok.Builder;

import java.util.Collections;

public class PhysicalPinsentryIneligible extends PhysicalPinsentry {

    @Builder
    public PhysicalPinsentryIneligible(final Ineligible ineligible, final PinsentryFunction function) {
        super(ineligible, function, Collections.emptyList());
    }

}
