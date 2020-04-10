package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;

public class NoPinsentryDevice extends Ineligible {

    public NoPinsentryDevice() {
        super("no pinsentry device");
    }

}
