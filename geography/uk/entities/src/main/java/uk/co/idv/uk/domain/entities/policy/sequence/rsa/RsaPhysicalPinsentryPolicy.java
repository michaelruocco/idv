package uk.co.idv.uk.domain.entities.policy.sequence.rsa;

import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.policy.PhysicalPinsentryPolicy;

public class RsaPhysicalPinsentryPolicy extends PhysicalPinsentryPolicy {

    public RsaPhysicalPinsentryPolicy() {
        super(new RsaPinsentryParams());
    }

}
