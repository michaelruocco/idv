package uk.co.idv.domain.entities.verificationcontext.sequence.policy;

import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.domain.entities.policy.PolicyRequest;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequences;

import java.util.UUID;

public interface VerificationSequencesPolicy {

    UUID getId();

    PolicyLevel getLevel();

    boolean appliesTo(final PolicyRequest request);

    VerificationSequences buildSequences(final VerificationSequencesPolicyRequest request);

}
