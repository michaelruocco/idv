package uk.co.mruoc.idv.mongo.lockout.dao;

import uk.co.mruoc.idv.lockout.domain.model.AbstractLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.LockoutStateCalculatorFactory.LockoutTypeNotSupportedException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class LockoutPolicyParametersConverterDelegator {

    private final Collection<LockoutPolicyParametersConverter> converters;

    public LockoutPolicyParametersConverterDelegator(final LockoutPolicyParametersConverter... converters) {
        this(Arrays.asList(converters));
    }

    public LockoutPolicyParametersConverterDelegator(final Collection<LockoutPolicyParametersConverter> converters) {
        this.converters = converters;
    }

    public AbstractLockoutPolicyParameters toParameters(final LockoutPolicyDocument document) {
        final String type = document.getLockoutType();
        final Optional<LockoutPolicyParametersConverter> policyConverter = findConverter(type);
        return policyConverter
                .map(converter -> converter.toParameters(document))
                .orElseThrow(() -> new LockoutTypeNotSupportedException(type));
    }

    public LockoutPolicyDocument toDocument(final AbstractLockoutPolicyParameters parameters) {
        final String type = parameters.getLockoutType();
        final Optional<LockoutPolicyParametersConverter> activityConverter = findConverter(type);
        return activityConverter
                .map(converter -> converter.toDocument(parameters))
                .orElseThrow(() -> new LockoutTypeNotSupportedException(type));
    }

    private Optional<LockoutPolicyParametersConverter> findConverter(final String lockoutType) {
        return converters.stream()
                .filter(converter -> converter.supportsType(lockoutType))
                .findFirst();
    }

}
