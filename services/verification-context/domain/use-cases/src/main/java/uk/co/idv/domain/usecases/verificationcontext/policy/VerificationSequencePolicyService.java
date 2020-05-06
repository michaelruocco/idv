package uk.co.idv.domain.usecases.verificationcontext.policy;

import lombok.Getter;
import uk.co.idv.domain.entities.policy.PolicyRequest;
import uk.co.idv.domain.entities.verificationcontext.sequence.policy.VerificationSequencesPolicy;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public interface VerificationSequencePolicyService {

    default void create(Collection<VerificationSequencesPolicy> policies) {
        policies.forEach(this::create);
    }

    void create(VerificationSequencesPolicy policy);

    void update(VerificationSequencesPolicy policy);

    VerificationSequencesPolicy load(UUID id);

    VerificationSequencesPolicy load(final PolicyRequest request);

    Collection<VerificationSequencesPolicy> loadAll();

    @Getter
    class RequestedVerificationSequencesPolicyNotFoundException extends RuntimeException {

        private final PolicyRequest request;

        public RequestedVerificationSequencesPolicyNotFoundException(final PolicyRequest request) {
            super(String.format("channelId %s activity %s aliasType %s",
                    request.getChannelId(),
                    request.getActivityName(),
                    request.getAliasType()));
            this.request = request;
        }

    }

    class VerificationSequencesPolicyNotFoundException extends RuntimeException {

        public VerificationSequencesPolicyNotFoundException(final UUID id) {
            super(id.toString());
        }

    }

    class VerificationSequencesPolicyAlreadyExistsException extends RuntimeException {

        public VerificationSequencesPolicyAlreadyExistsException(final Collection<VerificationSequencesPolicy> policies) {
            super(toMessage(policies));
        }

        private static String toMessage(final Collection<VerificationSequencesPolicy> policies) {
            return String.format("verification sequences policy %s already exists for same lockout level", toString(policies));
        }

        private static String toString(final Collection<VerificationSequencesPolicy> policies) {
            return policies.stream()
                    .map(VerificationSequencesPolicy::getId)
                    .map(UUID::toString)
                    .collect(Collectors.joining(","));
        }

    }

}
