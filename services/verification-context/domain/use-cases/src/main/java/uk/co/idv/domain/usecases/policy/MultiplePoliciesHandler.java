package uk.co.idv.domain.usecases.policy;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.policy.Policy;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
public class MultiplePoliciesHandler<T extends Policy> {

    public Optional<T> extractPolicy(final List<T> policies) {
        switch (policies.size()) {
            case 0:
                return Optional.empty();
            case 1:
                return Optional.of(policies.get(0));
            default:
                return Optional.of(handleMultiple(policies));
        }
    }

    private T handleMultiple(final List<T> policies) {
        log.info("handling multiple policies {}", policies);
        final Optional<T> aliasLevelPolicy = extractFirstAliasLevelPolicy(policies);
        if (aliasLevelPolicy.isPresent()) {
            final T policy = aliasLevelPolicy.get();
            log.info("returning alias level policy {}", policy);
            return policy;
        }
        final T policy = policies.get(0);
        log.info("defaulting to first policy {}", policy);
        return policy;
    }

    private Optional<T> extractFirstAliasLevelPolicy(final Collection<T> policies) {
        return policies.stream()
                .filter(Policy::isAliasLevel)
                .findFirst();
    }

}
