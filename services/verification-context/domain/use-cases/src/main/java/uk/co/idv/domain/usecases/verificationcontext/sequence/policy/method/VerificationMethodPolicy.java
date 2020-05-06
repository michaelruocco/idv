package uk.co.idv.domain.usecases.verificationcontext.sequence.policy.method;

import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;

public interface VerificationMethodPolicy {

    String getName();

    VerificationMethod buildMethod(final LoadSequencesRequest request);

}
