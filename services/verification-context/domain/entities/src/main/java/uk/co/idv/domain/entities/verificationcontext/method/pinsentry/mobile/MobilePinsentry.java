package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile;

import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;

public interface MobilePinsentry extends VerificationMethod {

    String NAME = "mobile-pinsentry";

    PinsentryFunction getFunction();

}
