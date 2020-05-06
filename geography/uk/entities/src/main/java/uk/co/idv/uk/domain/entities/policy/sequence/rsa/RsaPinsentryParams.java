package uk.co.idv.uk.domain.entities.policy.sequence.rsa;

import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.DefaultPinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryFunction;

import java.time.Duration;

public class RsaPinsentryParams extends DefaultPinsentryParams {

    public RsaPinsentryParams() {
        super(3, Duration.ofMinutes(5), PinsentryFunction.RESPOND);
    }

}
