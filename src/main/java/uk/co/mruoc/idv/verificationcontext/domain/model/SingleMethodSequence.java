package uk.co.mruoc.idv.verificationcontext.domain.model;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardCredentials;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSms;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotification;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Slf4j
public class SingleMethodSequence implements VerificationSequence {

    private final VerificationMethod method;
    private final VerificationResults results;

    public SingleMethodSequence(final VerificationMethod method) {
        this(method, new VerificationResults());
    }

    public SingleMethodSequence(final VerificationMethod method, final VerificationResult result) {
        this(method, new VerificationResults(result));
    }

    public SingleMethodSequence(final VerificationMethod method, final VerificationResults results) {
        this.method = method;
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
        return Collections.singleton(method);
    }

    @Override
    public Duration getDuration() {
        return method.getDuration();
    }

    @Override
    public boolean containsMethod(final String methodName) {
        return method.hasName(methodName);
    }

    @Override
    public boolean isEligible() {
        return method.isEligible();
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
    public VerificationResults getResults() {
        return results;
    }

    @Override
    public boolean isComplete() {
        return !results.isEmpty();
    }

    @Override
    public boolean isSuccessful() {
        return results.containsSuccessful();
    }

    @Override
    public String getName() {
        return method.getName();
    }

    @Override
    public boolean hasNextMethod(final String methodName) {
        return containsMethod(methodName);
    }

    private <T> Optional<T> castMethodTo(final Class<T> type) {
        if (type.isAssignableFrom(method.getClass())) {
            return Optional.of(type.cast(method));
        }
        return Optional.empty();
    }

    private VerificationSequence addResult(final VerificationResult result) {
        final VerificationResults updatedResults = new VerificationResults(results);
        return new SingleMethodSequence(method, updatedResults.add(result));
    }

}
