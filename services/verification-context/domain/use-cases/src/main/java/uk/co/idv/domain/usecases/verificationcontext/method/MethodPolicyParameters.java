package uk.co.idv.domain.usecases.verificationcontext.method;

import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;

public interface MethodPolicyParameters {

    String getMethodName();

    VerificationMethod buildMethod(final LoadSequencesRequest request);

}
