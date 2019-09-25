package uk.co.mruoc.idv.verificationcontext.domain.model;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardCredentials;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSms;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotification;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class MultipleMethodSequence implements VerificationSequence {

    private final String name;
    private final Collection<VerificationMethod> methods;
    private final Collection<VerificationResult> results;

    public MultipleMethodSequence(final Collection<VerificationMethod> methods) {
        this(buildDefaultName(methods), methods, Collections.emptyList());
    }

    public MultipleMethodSequence(final Collection<VerificationMethod> methods, final VerificationResult result) {
        this(buildDefaultName(methods), methods, Collections.singleton(result));
    }

    public MultipleMethodSequence(final Collection<VerificationMethod> methods, final Collection<VerificationResult> results) {
        this(buildDefaultName(methods), methods, results);
    }

    public MultipleMethodSequence(final String name, final Collection<VerificationMethod> methods) {
        this(name, methods, Collections.emptyList());
    }

    public MultipleMethodSequence(final String name, final Collection<VerificationMethod> methods, final Collection<VerificationResult> results) {
        this.name = name;
        this.methods = methods;
        this.results = results;
    }

    @Override
    public Optional<PhysicalPinsentry> getPhysicalPinsentry() {
        return castMethodTo(PhysicalPinsentry.class);
    }

    @Override
    public Optional<MobilePinsentry> getMobilePinsentry() {
        return castMethodTo(MobilePinsentry.class);
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
    public Optional<CardCredentials> getCardCredentials() {
        return castMethodTo(CardCredentials.class);
    }

    @Override
    public Collection<VerificationMethod> getMethods() {
        return Collections.unmodifiableCollection(methods);
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
    public boolean hasResults() {
        return !results.isEmpty();
    }

    @Override
    public Collection<VerificationResult> getResults() {
        return Collections.unmodifiableCollection(results);
    }

    @Override
    public boolean isComplete() {
        final Collection<String> methodNames = getMethodNames();
        final Collection<String> resultMethodNames = getResultMethodNames();
        return resultMethodNames.containsAll(methodNames);
    }

    @Override
    public boolean isSuccessful() {
        final Collection<String> methodNames = getMethodNames();
        return methodNames.stream().allMatch(this::hasSuccessfulResult);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasNextMethod(final String methodName) {
        final Collection<String> methodNames = getMethodNames();
        final Collection<String> resultMethodNames = getResultMethodNames();
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
        final Collection<VerificationResult> updatedResults = new ArrayList<>(results);
        updatedResults.add(result);
        return new MultipleMethodSequence(name, methods, updatedResults);
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

    private Collection<String> getResultMethodNames() {
        return results.stream()
                .map(VerificationResult::getMethodName)
                .collect(Collectors.toSet());
    }

    private boolean hasSuccessfulResult(final String methodName) {
        return results.stream()
                .filter(result -> result.hasMethodName(methodName))
                .anyMatch(VerificationResult::isSuccessful);
    }

    private static String buildDefaultName(final Collection<VerificationMethod> methods) {
        return methods.stream()
                .map(VerificationMethod::getName)
                .collect(Collectors.joining("_"));
    }

}
