package uk.co.mruoc.idv.verificationcontext.domain.model;

import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardCredentials;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSms;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotification;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;

public interface VerificationSequence {

    Optional<PhysicalPinsentry> getPhysicalPinsentry();

    Optional<MobilePinsentry> getMobilePinsentry();

    Optional<PushNotification> getPushNotification();

    Optional<OneTimePasscodeSms> getOneTimePasscodeSms();

    Optional<CardCredentials> getCardCredentials();

    Duration getDuration();

    boolean containsMethod(final String name);

    boolean isEligible();

    VerificationSequence addResultIfContainsMethod(final VerificationResult result);

    Collection<VerificationResult> getResults();

    boolean isComplete();

    boolean isSuccessful();

}
