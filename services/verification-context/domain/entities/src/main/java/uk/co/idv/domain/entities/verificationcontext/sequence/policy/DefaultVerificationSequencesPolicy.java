package uk.co.idv.domain.entities.verificationcontext.sequence.policy;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequences;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DefaultVerificationSequencesPolicy implements VerificationSequencesPolicy {

    private final PolicyLevel level;
    private final Collection<VerificationSequencePolicy> sequencePolicies;

    @Override
    public boolean appliesTo(final VerificationSequencesPolicyRequest request) {
        return level.appliesTo(request);
    }

    @Override
    public VerificationSequences buildSequences(final VerificationSequencesPolicyRequest request) {
        final Collection<VerificationSequence> sequences = sequencePolicies.stream()
                .map(sequencePolicy -> sequencePolicy.buildSequence(request))
                .collect(Collectors.toList());
        return new VerificationSequences(sequences);
    }

}
