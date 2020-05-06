package uk.co.idv.domain.entities.verificationcontext.sequence.policy;

import java.util.Collection;

public interface VerificationSequencesPolicyProvider {

    Collection<VerificationSequencesPolicy> getPolicies();

}
