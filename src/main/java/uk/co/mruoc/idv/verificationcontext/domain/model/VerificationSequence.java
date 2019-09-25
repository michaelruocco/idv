package uk.co.mruoc.idv.verificationcontext.domain.model;

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
import java.util.Optional;

public interface VerificationSequence {

    Optional<PhysicalPinsentry> getPhysicalPinsentry();

    Optional<MobilePinsentry> getMobilePinsentry();

    Optional<PushNotification> getPushNotification();

    Optional<OneTimePasscodeSms> getOneTimePasscodeSms();

    Optional<CardCredentials> getCardCredentials();

    Collection<VerificationMethod> getMethods();

    Duration getDuration();

    boolean containsMethod(String methodName);

    boolean isEligible();

    VerificationSequence addResultIfHasNextMethod(VerificationResult result);

    boolean hasResults();

    VerificationResults getResults();

    boolean isComplete();

    boolean isSuccessful();

    String getName();

    boolean hasNextMethod(String methodName);

}
