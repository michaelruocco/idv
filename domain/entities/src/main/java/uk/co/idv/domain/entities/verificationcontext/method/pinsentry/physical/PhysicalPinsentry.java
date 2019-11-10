package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;

public interface PhysicalPinsentry extends VerificationMethod {

    String NAME = "physical-pinsentry";

    PinsentryFunction getFunction();

}
