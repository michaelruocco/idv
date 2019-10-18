package uk.co.mruoc.idv.verificationcontext.domain.model.method;

public interface PhysicalPinsentry extends VerificationMethod {

    String NAME = "physical-pinsentry";

    PinsentryFunction getFunction();

}
