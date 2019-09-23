package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import lombok.RequiredArgsConstructor;

import java.time.Duration;

@RequiredArgsConstructor
public class PushNotification implements VerificationMethod {

    private static final String NAME = "push-notification";

    private final Eligibility eligibility;
    private final Duration duration;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }

    @Override
    public Eligibility getEligibility() {
        return eligibility;
    }

}
