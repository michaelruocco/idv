package uk.co.mruoc.idv.verificationcontext.domain.model.method;

public interface MobilePinsentry extends VerificationMethod {

    String NAME = "mobile-pinsentry";

    PinsentryFunction getFunction();

}
