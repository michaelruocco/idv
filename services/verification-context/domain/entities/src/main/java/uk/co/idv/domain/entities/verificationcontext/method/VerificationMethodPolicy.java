package uk.co.idv.domain.entities.verificationcontext.method;

public interface VerificationMethodPolicy {

    String getName();

    VerificationMethod buildMethod(final VerificationMethodPolicyRequest request);

}
