package uk.co.idv.domain.usecases.policy;

import lombok.Getter;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.entities.policy.Policy;
import uk.co.idv.domain.entities.policy.PolicyRequest;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public interface PolicyService<T extends Policy> {

    default void create(Collection<T> policies) {
        policies.forEach(this::create);
    }

    void create(T policy);

    void update(T policy);

    T load(UUID id);

    T load(final LockoutRequest request);

    Collection<T> loadAll();

    @Getter
    class RequestedPolicyNotFoundException extends RuntimeException {

        private final PolicyRequest request;

        public RequestedPolicyNotFoundException(final PolicyRequest request) {
            super(String.format("channelId %s activity %s aliasType %s",
                    request.getChannelId(),
                    request.getActivityName(),
                    request.getAliasType()));
            this.request = request;
        }

    }

    class PolicyNotFoundException extends RuntimeException {

        public PolicyNotFoundException(final UUID id) {
            super(id.toString());
        }

    }

    class PoliciesAlreadyExistException extends RuntimeException {

        public PoliciesAlreadyExistException(final Collection<UUID> ids) {
            super(toMessage(ids));
        }

        private static String toMessage(final Collection<UUID> ids) {
            return String.format("policies %s already exist for same lockout level", toString(ids));
        }

        private static String toString(final Collection<UUID> ids) {
            return ids.stream()
                    .map(UUID::toString)
                    .collect(Collectors.joining(","));
        }

    }

}
