package uk.co.idv.repository.mongo.lockout.policy;

import uk.co.idv.domain.entities.lockout.LockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.LockoutStateCalculatorFactory.LockoutTypeNotSupportedException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class LockoutPolicyDocumentConverterDelegator {

    private final Collection<LockoutPolicyDocumentConverter> converters;

    public LockoutPolicyDocumentConverterDelegator(final LockoutPolicyDocumentConverter... converters) {
        this(Arrays.asList(converters));
    }

    public LockoutPolicyDocumentConverterDelegator(final Collection<LockoutPolicyDocumentConverter> converters) {
        this.converters = converters;
    }

    public LockoutPolicyParameters toParameters(final LockoutPolicyDocument document) {
        final String type = document.getLockoutType();
        final Optional<LockoutPolicyDocumentConverter> policyConverter = findConverter(type);
        return policyConverter
                .map(converter -> converter.toParameters(document))
                .orElseThrow(() -> new LockoutTypeNotSupportedException(type));
    }

    public LockoutPolicyDocument toDocument(final LockoutPolicyParameters parameters) {
        final String type = parameters.getLockoutType();
        final Optional<LockoutPolicyDocumentConverter> activityConverter = findConverter(type);
        return activityConverter
                .map(converter -> converter.toDocument(parameters))
                .orElseThrow(() -> new LockoutTypeNotSupportedException(type));
    }

    private Optional<LockoutPolicyDocumentConverter> findConverter(final String lockoutType) {
        return converters.stream()
                .filter(converter -> converter.supportsType(lockoutType))
                .findFirst();
    }

}
