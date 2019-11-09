package uk.co.idv.domain.entities.verificationcontext.method;

public interface PhysicalPinsentry extends VerificationMethod {

    String NAME = "physical-pinsentry";

    PinsentryFunction getFunction();

}
