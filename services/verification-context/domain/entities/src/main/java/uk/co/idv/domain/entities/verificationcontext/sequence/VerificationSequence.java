package uk.co.idv.domain.entities.verificationcontext.sequence;

import lombok.Getter;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;

public interface VerificationSequence {

    Optional<PhysicalPinsentry> getPhysicalPinsentry();

    Optional<MobilePinsentry> getMobilePinsentry();

    Optional<PushNotification> getPushNotification();

    Optional<OneTimePasscode> getOneTimePasscode();

    Collection<VerificationMethod> getMethods();

    VerificationMethod getMethod(String methodName);

    Duration getDuration();

    boolean containsMethod(String methodName);

    boolean containsCompleteMethod(String methodName);

    boolean isEligible();

    boolean isComplete();

    boolean isSuccessful();

    void addResultIfHasNextMethod(VerificationResult result);

    String getName();

    boolean hasNextMethod(String methodName);

    @Getter
    class MethodNotFoundInSequenceException extends RuntimeException {

        private final String methodName;
        private final String sequenceName;

        public MethodNotFoundInSequenceException(final String methodName, final String sequenceName) {
            super(String.format("cannot find method %s in sequence %s sequence", methodName, sequenceName));
            this.methodName = methodName;
            this.sequenceName = sequenceName;
        }

    }

}
