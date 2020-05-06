package uk.co.idv.domain.usecases.verificationcontext.sequence.policy.method;

import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.usecases.verificationcontext.sequence.policy.VerificationSequencesPolicyRequest;

public interface VerificationMethodPolicy {

    String getName();

    VerificationMethod buildMethod(final VerificationSequencesPolicyRequest request);

}
