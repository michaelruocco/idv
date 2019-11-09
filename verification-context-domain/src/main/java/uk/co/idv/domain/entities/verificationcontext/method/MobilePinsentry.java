package uk.co.idv.domain.entities.verificationcontext.method;

public interface MobilePinsentry extends VerificationMethod {

    String NAME = "mobile-pinsentry";

    PinsentryFunction getFunction();

}
