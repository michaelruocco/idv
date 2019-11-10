package uk.co.idv.domain.entities.verificationcontext;

import lombok.Getter;
import uk.co.idv.domain.entities.verificationcontext.method.cardcredentials.CardCredentialsEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryEligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeSms;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;

public interface VerificationSequence {

    Optional<PhysicalPinsentry> getPhysicalPinsentry();

    Optional<MobilePinsentryEligible> getMobilePinsentry();

    Optional<PushNotification> getPushNotification();

    Optional<OneTimePasscodeSms> getOneTimePasscodeSms();

    Optional<CardCredentialsEligible> getCardCredentials();

    Collection<VerificationMethod> getMethods();

    VerificationMethod getMethod(String methodName);

    Duration getDuration();

    boolean containsMethod(String methodName);

    boolean containsCompleteMethod(String methodName);

    boolean isEligible();

    boolean isComplete();

    boolean isSuccessful();

    VerificationSequence addResultIfHasNextMethod(VerificationResult result);

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
