package uk.co.idv.domain.entities.verificationcontext;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryEligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@EqualsAndHashCode
@ToString
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
    public Optional<OneTimePasscode> getOneTimePasscode() {
        return castMethodTo(OneTimePasscode.class);
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
    public boolean containsCompleteMethod(final String methodName) {
        return containsMethod(methodName) && isComplete();
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
        return containsMethod(methodName) && !isComplete();
    }

    private <T> Optional<T> castMethodTo(final Class<T> type) {
        if (type.isAssignableFrom(method.getClass())) {
            return Optional.of(type.cast(method));
        }
        return Optional.empty();
    }

}
