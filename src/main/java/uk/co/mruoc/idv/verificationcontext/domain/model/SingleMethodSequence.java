package uk.co.mruoc.idv.verificationcontext.domain.model;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
public class SingleMethodSequence implements VerificationSequence {

    private final VerificationMethod method;

    public SingleMethodSequence(final VerificationMethod method) {
        this.method = method;
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
        return Collections.singleton(method);
    }

    @Override
    public VerificationMethod getMethod(final String methodName) {
        if (method.hasName(methodName)) {
            return method;
        }
        throw new MethodNotFoundInSequenceException(methodName, getName());
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
        if (containsMethod(result.getMethodName())) {
            return new SingleMethodSequence(method.addResult(result));
        }
        return this;
    }

    @Override
    public boolean isComplete() {
        return method.isComplete();
    }

    @Override
    public boolean isSuccessful() {
        return method.isSuccessful();
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

}
