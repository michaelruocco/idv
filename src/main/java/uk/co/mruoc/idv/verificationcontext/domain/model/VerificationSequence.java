package uk.co.mruoc.idv.verificationcontext.domain.model;

import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardCredentialsEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentryEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSms;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotification;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

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

    boolean isEligible();

    boolean isComplete();

    boolean isSuccessful();

    VerificationSequence addResultIfHasNextMethod(VerificationResult result);

    String getName();

    boolean hasNextMethod(String methodName);

    class MethodNotFoundInSequenceException extends RuntimeException {

        public MethodNotFoundInSequenceException(final String methodName, final String sequenceName) {
            super(String.format("cannot find method %s in sequence %s sequence", methodName, sequenceName));
        }

    }

}
