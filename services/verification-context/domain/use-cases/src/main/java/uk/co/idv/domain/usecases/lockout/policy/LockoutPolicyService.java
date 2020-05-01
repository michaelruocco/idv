package uk.co.idv.domain.usecases.lockout.policy;

import lombok.Getter;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.LockoutRequest;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public interface LockoutPolicyService {

    default void create(Collection<LockoutPolicy> policies) {
        policies.forEach(this::create);
    }

    void create(LockoutPolicy policy);

    void update(LockoutPolicy policy);

    LockoutPolicy load(UUID id);

    LockoutPolicy load(final LockoutRequest request);

    Collection<LockoutPolicy> loadAll();

    @Getter
    class RequestedLockoutPolicyNotFoundException extends RuntimeException {

        private final LockoutRequest request;

        public RequestedLockoutPolicyNotFoundException(final LockoutRequest request) {
            super(String.format("channelId %s activity %s aliasType %s",
                    request.getChannelId(),
                    request.getActivityName(),
                    request.getAliasType()));
            this.request = request;
        }

    }

    class LockoutPolicyNotFoundException extends RuntimeException {

        public LockoutPolicyNotFoundException(final UUID id) {
            super(id.toString());
        }

    }

    class LockoutPoliciesAlreadyExistException extends RuntimeException {

        public LockoutPoliciesAlreadyExistException(final Collection<LockoutPolicy> policies) {
            super(toMessage(policies));
        }

        private static String toMessage(final Collection<LockoutPolicy> policies) {
            return String.format("lockout policies %s already exist for same lockout level", toString(policies));
        }

        private static String toString(final Collection<LockoutPolicy> policies) {
            return policies.stream()
                    .map(LockoutPolicy::getId)
                    .map(UUID::toString)
                    .collect(Collectors.joining(","));
        }

    }

}
