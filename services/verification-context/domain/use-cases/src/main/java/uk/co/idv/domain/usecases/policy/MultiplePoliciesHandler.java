package uk.co.idv.domain.usecases.policy;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.policy.Policy;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
public class MultiplePoliciesHandler<T extends Policy> {

    public Optional<T> extractPolicy(final List<T> policies) {
        if (policies.isEmpty()) {
            return Optional.empty();
        }
        if (policies.size() == 1) {
            return Optional.of(policies.get(0));
        }
        return Optional.of(handleMultiple(policies));
    }

    private T handleMultiple(final List<T> policies) {
        log.info("handling multiple policies {}", policies);
        return extractFirstAliasLevelPolicy(policies)
                .map(this::logAliasLevel)
                .orElseGet(() -> logDefaultToFirst(policies.get(0)));
    }

    private Optional<T> extractFirstAliasLevelPolicy(final Collection<T> policies) {
        return policies.stream()
                .filter(Policy::isAliasLevel)
                .findFirst();
    }

    private T logAliasLevel(final T policy) {
        log.info("returning alias level policy {}", policy);
        return policy;
    }

    private T logDefaultToFirst(final T policy) {
        log.info("defaulting to first policy {}", policy);
        return policy;
    }

}
