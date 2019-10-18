package uk.co.mruoc.idv.verificationcontext.domain.model;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardCredentialsEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentryEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSms;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotification;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class MultipleMethodSequence implements VerificationSequence {

    private final String name;
    private final Collection<VerificationMethod> methods;

    public MultipleMethodSequence(final Collection<VerificationMethod> methods) {
        this(buildDefaultName(methods), methods);
    }

    public MultipleMethodSequence(final String name, final Collection<VerificationMethod> methods) {
        this.name = name;
        this.methods = methods;
    }

    @Override
    public Optional<PhysicalPinsentry> getPhysicalPinsentry() {
        return castMethodTo(PhysicalPinsentry.class);
    }

    @Override
    public Optional<MobilePinsentryEligible> getMobilePinsentry() {
        return castMethodTo(MobilePinsentryEligible.class);
    }

    @Override
    public Optional<PushNotification> getPushNotification() {
        return castMethodTo(PushNotification.class);
    }

    @Override
    public Optional<OneTimePasscodeSms> getOneTimePasscodeSms() {
        return castMethodTo(OneTimePasscodeSms.class);
    }

    @Override
    public Optional<CardCredentialsEligible> getCardCredentials() {
        return castMethodTo(CardCredentialsEligible.class);
    }

    @Override
    public Collection<VerificationMethod> getMethods() {
        return Collections.unmodifiableCollection(methods);
    }

    @Override
    public VerificationMethod getMethod(String methodName) {
        return getMethodOptional(methodName)
                .orElseThrow(() -> new MethodNotFoundInSequenceException(methodName, getName()));
    }

    @Override
    public Duration getDuration() {
        return calculateLongestMethodDuration();
    }

    @Override
    public boolean containsMethod(final String name) {
        return methods.stream().anyMatch(method -> method.hasName(name));
    }

    @Override
    public boolean containsCompleteMethod(final String methodName) {
        final Optional<VerificationMethod> method = getMethodOptional(methodName);
        return method.map(VerificationMethod::isComplete).orElse(false);
    }

    @Override
    public boolean isEligible() {
        return methods.stream().allMatch(VerificationMethod::isEligible);
    }

    @Override
    public VerificationSequence addResultIfHasNextMethod(final VerificationResult result) {
        if (hasNextMethod(result.getMethodName())) {
            return addResult(result);
        }
        return this;
    }

    @Override
    public boolean isComplete() {
        return methods.stream().allMatch(VerificationMethod::isComplete);
    }

    @Override
    public boolean isSuccessful() {
        return methods.stream().allMatch(VerificationMethod::isSuccessful);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasNextMethod(final String methodName) {
        final Collection<String> methodNames = getMethodNames();
        final Collection<String> resultMethodNames = getMethodWithResultsNames();
        final Collection<String> incompleteMethodNames = CollectionUtils.subtract(methodNames, resultMethodNames);
        final Optional<String> nextMethodName = incompleteMethodNames.stream().findFirst();
        return nextMethodName.map(value -> value.equals(methodName)).orElse(false);
    }

    private <T> Optional<T> castMethodTo(final Class<T> type) {
        return methods.stream()
                .filter(method -> type.isAssignableFrom(method.getClass()))
                .map(type::cast)
                .findFirst();
    }

    private VerificationSequence addResult(final VerificationResult result) {
        final String resultMethodName = result.getMethodName();
        final VerificationMethod resultMethod = getMethod(resultMethodName);
        if (!resultMethod.isEligible()) {
            return this;
        }
        final Collection<VerificationMethod> updatedMethods = getMethodsExcluding(resultMethodName);
        updatedMethods.add(resultMethod.addResult(result));
        return new MultipleMethodSequence(name, updatedMethods);
    }

    private Collection<VerificationMethod> getMethodsExcluding(final String methodName) {
        return methods.stream()
                .filter(method -> !method.hasName(methodName))
                .collect(Collectors.toList());
    }

    private Duration calculateLongestMethodDuration() {
        Duration longest = null;
        for (final VerificationMethod method : methods) {
            final Duration methodDuration = method.getDuration();
            if (longest == null || methodDuration.toMillis() > longest.toMillis()) {
                longest = methodDuration;
            }
        }
        return longest;
    }

    private Collection<String> getMethodNames() {
        return methods.stream()
                .map(VerificationMethod::getName)
                .collect(Collectors.toSet());
    }

    private Collection<String> getMethodWithResultsNames() {
        return methods.stream()
                .filter(VerificationMethod::hasResults)
                .map(VerificationMethod::getName)
                .collect(Collectors.toSet());
    }

    private Optional<VerificationMethod> getMethodOptional(String methodName) {
        return methods.stream()
                .filter(method -> method.hasName(methodName))
                .findFirst();
    }

    private static String buildDefaultName(final Collection<VerificationMethod> methods) {
        return methods.stream()
                .map(VerificationMethod::getName)
                .collect(Collectors.joining("_"));
    }

}
