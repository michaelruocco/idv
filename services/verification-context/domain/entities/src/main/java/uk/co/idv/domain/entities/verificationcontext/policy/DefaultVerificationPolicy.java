package uk.co.idv.domain.entities.verificationcontext.policy;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequences;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Builder
public class DefaultVerificationPolicy implements VerificationPolicy {

    private final UUID id;
    private final PolicyLevel level;
    private final Collection<VerificationSequencePolicy> sequencePolicies;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public VerificationSequences buildSequences(final VerificationPolicyRequest request) {
        final Collection<VerificationSequence> sequences = sequencePolicies.stream()
                .map(sequencePolicy -> sequencePolicy.buildSequence(request))
                .collect(Collectors.toList());
        return new VerificationSequences(sequences);
    }

}
