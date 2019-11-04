package uk.co.mruoc.idv.mongo.lockout.dao.policy;

import uk.co.mruoc.idv.lockout.domain.model.AbstractLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.LockoutStateCalculatorFactory.LockoutTypeNotSupportedException;

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

    public AbstractLockoutPolicyParameters toParameters(final LockoutPolicyDocument document) {
        final String type = document.getLockoutType();
        final Optional<LockoutPolicyDocumentConverter> policyConverter = findConverter(type);
        return policyConverter
                .map(converter -> converter.toParameters(document))
                .orElseThrow(() -> new LockoutTypeNotSupportedException(type));
    }

    public LockoutPolicyDocument toDocument(final AbstractLockoutPolicyParameters parameters) {
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
