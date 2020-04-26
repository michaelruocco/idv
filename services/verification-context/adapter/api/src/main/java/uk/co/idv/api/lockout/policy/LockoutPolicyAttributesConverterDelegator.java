package uk.co.idv.api.lockout.policy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.lockout.exception.LockoutTypeNotSupportedException;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class LockoutPolicyAttributesConverterDelegator {

    private final Collection<LockoutPolicyAttributesConverter> converters;

    public LockoutPolicyAttributesConverterDelegator(final LockoutPolicyAttributesConverter... converters) {
        this(Arrays.asList(converters));
    }

    public LockoutPolicy toPolicy(final LockoutPolicyAttributes attributes) {
        final String type = attributes.getLockoutType();
        return findConverter(type)
                .map(converter -> converter.toPolicy(attributes))
                .orElseThrow(() -> new LockoutTypeNotSupportedException(type));
    }

    public LockoutPolicyAttributes toAttributes(final LockoutPolicy policy) {
        final String type = policy.getType();
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
