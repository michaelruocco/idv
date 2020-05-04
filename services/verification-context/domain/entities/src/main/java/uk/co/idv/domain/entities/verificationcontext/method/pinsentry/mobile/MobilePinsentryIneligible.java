package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile;

import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.IneligiblePinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;

public class MobilePinsentryIneligible extends MobilePinsentry {

    public MobilePinsentryIneligible(final PinsentryFunction function, final Ineligible reason) {
        super(new IneligiblePinsentryParams(function), reason, new DefaultVerificationResults());
    }

}
