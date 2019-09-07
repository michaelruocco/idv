package uk.co.mruoc.verificationcontext.domain;

import uk.co.mruoc.verificationcontext.domain.method.CardCredentials;
import uk.co.mruoc.verificationcontext.domain.method.MobilePinsentry;
import uk.co.mruoc.verificationcontext.domain.method.OneTimePasscodeSms;
import uk.co.mruoc.verificationcontext.domain.method.PhysicalPinsentry;
import uk.co.mruoc.verificationcontext.domain.method.PushNotification;

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

}
