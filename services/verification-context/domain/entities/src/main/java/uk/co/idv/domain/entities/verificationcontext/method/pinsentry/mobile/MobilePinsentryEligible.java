package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile;

import lombok.Builder;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

public class MobilePinsentryEligible extends MobilePinsentry {

    public MobilePinsentryEligible(final PinsentryParams params) {
        this(params, new DefaultVerificationResults());
    }

    @Builder
    public MobilePinsentryEligible(final PinsentryParams params, final VerificationResults results) {
        super(params, new Eligible(), results);
    }

}
