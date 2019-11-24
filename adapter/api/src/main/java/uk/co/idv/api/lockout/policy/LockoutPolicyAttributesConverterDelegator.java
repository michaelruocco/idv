package uk.co.idv.api.lockout.policy;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.lockout.exception.LockoutTypeNotSupportedException;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
@Slf4j
public class LockoutPolicyAttributesConverterDelegator {

    private final Collection<LockoutPolicyAttributesConverter> converters;

    public LockoutPolicyAttributesConverterDelegator(final LockoutPolicyAttributesConverter... converters) {
        this(Arrays.asList(converters));
    }

    public LockoutPolicyAttributesConverterDelegator(final Collection<LockoutPolicyAttributesConverter> converters) {
        this.converters = converters;
    }

    public Collection<LockoutPolicy> toPolicies(final Collection<LockoutPolicyAttributes> collection) {
        return collection.stream()
                .map(this::toPolicy)
                .collect(Collectors.toList());
    }

    public LockoutPolicy toPolicy(final LockoutPolicyAttributes attributes) {
        final String type = attributes.getLockoutType();
        return findConverter(type)
                .map(converter -> converter.toPolicy(attributes))
                .orElseThrow(() -> new LockoutTypeNotSupportedException(type));
    }

    public Collection<LockoutPolicyAttributes> toAttributes(final Collection<LockoutPolicy> policies) {
        return policies.stream()
                .map(this::toAttributes)
                .collect(Collectors.toList());
    }

    public LockoutPolicyAttributes toAttributes(final LockoutPolicy policy) {
        final String type = policy.getLockoutType();
        return findConverter(type)
                .map(converter -> converter.toAttributes(policy))
                .orElseThrow(() -> new LockoutTypeNotSupportedException(type));
    }

    private Optional<LockoutPolicyAttributesConverter> findConverter(final String type) {
        log.info("finding lockout policy attributes converter for type {} from {}", type, converters);
        return converters.stream()
                .filter(converter -> converter.supports(type))
                .findFirst();
    }

}
