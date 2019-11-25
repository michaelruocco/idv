package uk.co.idv.api.lockout.policy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class DefaultLockoutPolicyAttributesConverter {

    private final LockoutPolicyAttributesConverterDelegator delegator;

    public Collection<LockoutPolicy> toPolicies(final Collection<LockoutPolicyAttributes> collection) {
        return collection.stream()
                .map(this::toPolicy)
                .collect(Collectors.toList());
    }

    public LockoutPolicy toPolicy(final LockoutPolicyAttributes attributes) {
        return delegator.toPolicy(attributes);
    }

    public Collection<LockoutPolicyAttributes> toAttributes(final Collection<LockoutPolicy> policies) {
        return policies.stream()
                .map(this::toAttributes)
                .collect(Collectors.toList());
    }

    public LockoutPolicyAttributes toAttributes(final LockoutPolicy policy) {
        return delegator.toAttributes(policy);
    }

}
