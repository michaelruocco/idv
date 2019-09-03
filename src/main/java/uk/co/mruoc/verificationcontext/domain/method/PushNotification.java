package uk.co.mruoc.verificationcontext.domain.method;

import java.time.Duration;

public class PushNotification implements VerificationMethod {

    private final Eligibility eligibility;

    public PushNotification(final Eligibility eligibility) {
        this.eligibility = eligibility;
    }

    @Override
    public String getName() {
        return Names.PUSH_NOTIFICATION;
    }

    @Override
    public Duration getDuration() {
        return Duration.ofMinutes(5);
    }

    @Override
    public boolean isEligible() {
        return eligibility.isEligible();
    }

    @Override
    public Eligibility getEligibility() {
        return eligibility;
    }

}
