package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile;

import uk.co.idv.domain.entities.verificationcontext.method.DefaultVerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

public class MobilePinsentry extends DefaultVerificationMethod {

    public static final String NAME = "mobile-pinsentry";

    private final PinsentryParams params;

    public MobilePinsentry(final PinsentryParams params,
                           final Eligibility eligibility,
                           final VerificationResults results) {
        super(NAME, params, eligibility, results);
        this.params = params;
    }

    public PinsentryFunction getFunction() {
        return params.getFunction();
    }

}
