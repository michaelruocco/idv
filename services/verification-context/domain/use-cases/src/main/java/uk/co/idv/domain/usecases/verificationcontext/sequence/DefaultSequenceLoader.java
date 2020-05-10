package uk.co.idv.domain.usecases.verificationcontext.sequence;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicy;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequences;
import uk.co.idv.domain.usecases.verificationcontext.policy.VerificationPolicyService;

@RequiredArgsConstructor
public class DefaultSequenceLoader implements SequenceLoader {

    private final VerificationPolicyService policyService;

    @Override
    public VerificationSequences loadSequences(final LoadSequencesRequest request) {
        final VerificationPolicy policy = policyService.load(request);
        return policy.buildSequences(request);
    }

}
