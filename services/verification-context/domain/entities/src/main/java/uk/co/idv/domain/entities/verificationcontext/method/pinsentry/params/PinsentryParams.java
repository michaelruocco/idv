package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params;

import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;

public interface PinsentryParams extends VerificationMethodParams {

    PinsentryFunction getFunction();

}
