package uk.co.idv.domain.usecases.verificationcontext.policy;

import uk.co.idv.domain.entities.policy.PolicyRequest;
import uk.co.idv.domain.entities.verificationcontext.sequence.policy.VerificationSequencesPolicy;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface VerificationSequencePolicyDao {

    void save(final VerificationSequencesPolicy policy);

    Optional<VerificationSequencesPolicy> load(final UUID id);

    Collection<VerificationSequencesPolicy> load(final PolicyRequest request);

    Collection<VerificationSequencesPolicy> load();

}
