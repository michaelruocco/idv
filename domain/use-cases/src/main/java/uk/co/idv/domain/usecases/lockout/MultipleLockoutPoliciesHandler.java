package uk.co.idv.domain.usecases.lockout;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
public class MultipleLockoutPoliciesHandler {

    public Optional<LockoutPolicy> extractPolicy(final List<LockoutPolicy> policies) {
        switch (policies.size()) {
            case 0:
                return Optional.empty();
            case 1:
                return Optional.of(policies.get(0));
            default:
                return Optional.of(handleMultiple(policies));
        }
    }

    private LockoutPolicy handleMultiple(final List<LockoutPolicy> policies) {
        log.info("handling multiple policies {}", policies);
        final Optional<LockoutPolicy> aliasLevelPolicy = extractFirstAliasLevelPolicy(policies);
        if (aliasLevelPolicy.isPresent()) {
            final LockoutPolicy policy = aliasLevelPolicy.get();
            log.info("returning alias level policy {}", policy);
            return policy;
        }
        final LockoutPolicy policy = policies.get(0);
        log.info("defaulting to first policy {}", policy);
        return policy;
    }

    private Optional<LockoutPolicy> extractFirstAliasLevelPolicy(final Collection<LockoutPolicy> policies) {
        return policies.stream()
                .filter(LockoutPolicy::isAliasLevel)
                .findFirst();
    }

}
