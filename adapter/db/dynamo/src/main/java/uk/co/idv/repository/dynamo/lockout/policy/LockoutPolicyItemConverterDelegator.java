package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.document.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import uk.co.idv.domain.entities.lockout.exception.LockoutTypeNotSupportedException;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class LockoutPolicyItemConverterDelegator {

    private final Collection<LockoutPolicyItemConverter> converters;

    public LockoutPolicyItemConverterDelegator(final LockoutPolicyItemConverter... converters) {
        this(Arrays.asList(converters));
    }

    public Collection<LockoutPolicy> toPolicies(final Iterable<Item> items) {
        return toPolicies(IterableUtils.toList(items));
    }

    public Collection<LockoutPolicy> toPolicies(final Collection<Item> items) {
        return items.stream()
                .map(this::toPolicy)
                .collect(Collectors.toList());
    }

    public LockoutPolicy toPolicy(final Item item) {
        log.info("converting item {} to policy", item);
        final String type = item.getString("lockoutType");
        final Optional<LockoutPolicyItemConverter> policyConverter = findConverter(type);
        return policyConverter
                .map(converter -> converter.toPolicy(item))
                .orElseThrow(() -> new LockoutTypeNotSupportedException(type));
    }

    public Item toItem(final LockoutPolicy policy) {
        final String type = policy.getLockoutType();
        final Optional<LockoutPolicyItemConverter> activityConverter = findConverter(type);
        return activityConverter
                .map(converter -> converter.toItem(policy))
                .orElseThrow(() -> new LockoutTypeNotSupportedException(type));
    }

    private Optional<LockoutPolicyItemConverter> findConverter(final String lockoutType) {
        log.info("finding lockout policy document converter for lockout type {}", lockoutType);
        return converters.stream()
                .filter(converter -> converter.supportsType(lockoutType))
                .findFirst();
    }

}