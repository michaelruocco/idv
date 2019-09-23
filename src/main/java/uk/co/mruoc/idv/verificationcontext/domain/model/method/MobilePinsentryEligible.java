package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import java.time.Duration;

public class MobilePinsentryEligible extends MobilePinsentry {

    public MobilePinsentryEligible(final PinsentryFunction function) {
        super(new Eligible(), Duration.ofMinutes(5), function);
    }

}
