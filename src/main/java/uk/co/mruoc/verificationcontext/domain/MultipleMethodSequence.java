package uk.co.mruoc.verificationcontext.domain;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.verificationcontext.domain.method.CardCredentials;
import uk.co.mruoc.verificationcontext.domain.method.MobilePinsentry;
import uk.co.mruoc.verificationcontext.domain.method.OneTimePasscodeSms;
import uk.co.mruoc.verificationcontext.domain.method.PhysicalPinsentry;
import uk.co.mruoc.verificationcontext.domain.method.PushNotification;
import uk.co.mruoc.verificationcontext.domain.method.VerificationMethod;
import uk.co.mruoc.verificationcontext.domain.result.VerificationResult;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@ToString
public class MultipleMethodSequence implements VerificationSequence {

    private final Collection<VerificationMethod> methods;
    private final Collection<VerificationResult> results;

    public MultipleMethodSequence(final Collection<VerificationMethod> methods) {
        this(methods, Collections.emptyList());
    }

    public MultipleMethodSequence(final Collection<VerificationMethod> methods, final VerificationResult result) {
        this(methods, Collections.singleton(result));
    }

    public MultipleMethodSequence(final Collection<VerificationMethod> methods, final Collection<VerificationResult> results) {
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
    public VerificationSequence addResultIfContainsMethod(final VerificationResult result) {
        if (containsMethod(result.getMethodName())) {
            return addResult(result);
        }
        logResultNotAdded(result);
        return this;
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

    private <T> Optional<T> castMethodTo(final Class<T> type) {
        return methods.stream()
                .filter(method -> type.isAssignableFrom(method.getClass()))
                .map(type::cast)
                .findFirst();
    }

    private VerificationSequence addResult(final VerificationResult result) {
        final Collection<VerificationResult> updatedResults = new ArrayList<>(results);
        updatedResults.add(result);
        return new MultipleMethodSequence(methods, updatedResults);
    }

    private void logResultNotAdded(final VerificationResult result) {
        log.info("result not added {} to sequence {} as sequence does not contain method with name {}",
                result,
                this,
                result.getMethodName());
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

}
