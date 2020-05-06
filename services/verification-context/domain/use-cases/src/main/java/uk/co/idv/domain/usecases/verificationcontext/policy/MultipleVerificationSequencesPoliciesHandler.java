package uk.co.idv.domain.usecases.verificationcontext.policy;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.verificationcontext.sequence.policy.VerificationSequencesPolicy;

import java.util.List;
import java.util.Optional;

@Slf4j
public class MultipleVerificationSequencesPoliciesHandler {

    public Optional<VerificationSequencesPolicy> extractPolicy(final List<VerificationSequencesPolicy> policies) {
        if (policies.size() == 1) {
            return Optional.of(policies.get(0));
        }
        log.warn("found {} verification sequences policies, returning empty", policies.size());
        return Optional.empty();
    }

}
