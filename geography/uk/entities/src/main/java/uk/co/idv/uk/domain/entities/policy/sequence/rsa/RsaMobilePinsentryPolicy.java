package uk.co.idv.uk.domain.entities.policy.sequence.rsa;

import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.policy.MobilePinsentryPolicy;

public class RsaMobilePinsentryPolicy extends MobilePinsentryPolicy {

    public RsaMobilePinsentryPolicy() {
        super(new RsaPinsentryParams());
    }

}
