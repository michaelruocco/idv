package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import java.time.Duration;

public class MobilePinsentryIneligible extends MobilePinsentry {

    public MobilePinsentryIneligible(final PinsentryFunction function) {
        super(new NoMobileApplication(), Duration.ZERO, function);
    }

}
