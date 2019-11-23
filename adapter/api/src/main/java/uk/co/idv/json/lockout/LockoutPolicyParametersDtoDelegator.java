package uk.co.idv.json.lockout;

import lombok.Builder;
import uk.co.idv.domain.entities.lockout.exception.LockoutTypeNotSupportedException;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
public class LockoutPolicyParametersDtoDelegator {

    private final Collection<LockoutPolicyParametersConverter> converters;

    public LockoutPolicyParametersDtoDelegator(final LockoutPolicyParametersConverter... converters) {
        this(Arrays.asList(converters));
    }

    public LockoutPolicyParametersDtoDelegator(final Collection<LockoutPolicyParametersConverter> converters) {
        this.converters = converters;
    }

    public Collection<LockoutPolicy> toPolicies(final Collection<LockoutPolicyDto> collection) {
        return collection.stream()
                .map(this::toPolicy)
                .collect(Collectors.toList());
    }

    public LockoutPolicy toPolicy(final LockoutPolicyDto parameters) {
        final String type = parameters.getLockoutType();
        return findConverter(type)
                .map(converter -> converter.toPolicy(parameters))
                .orElseThrow(() -> new LockoutTypeNotSupportedException(type));
    }

    public Collection<LockoutPolicyDto> toParameters(final Collection<LockoutPolicy> policies) {
        return policies.stream()
                .map(this::toParameters)
                .collect(Collectors.toList());
    }

    public LockoutPolicyDto toParameters(final LockoutPolicy policy) {
        final String type = policy.getLockoutType();
        return findConverter(type)
                .map(converter -> converter.toParameters(policy))
                .orElseThrow(() -> new LockoutTypeNotSupportedException(type));
    }

    private Optional<LockoutPolicyParametersConverter> findConverter(final String type) {
        return converters.stream()
                .filter(converter -> converter.supports(type))
                .findFirst();
    }

}
