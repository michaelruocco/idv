package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import java.time.Duration;

public class PushNotification implements VerificationMethod {

    private static final String NAME = "push-notification";
    private static final Duration DURATION = Duration.ofMinutes(5);

    private final Eligibility eligibility;

    public PushNotification(final Eligibility eligibility) {
        this.eligibility = eligibility;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Duration getDuration() {
        return DURATION;
    }

    @Override
    public Eligibility getEligibility() {
        return eligibility;
    }

}
