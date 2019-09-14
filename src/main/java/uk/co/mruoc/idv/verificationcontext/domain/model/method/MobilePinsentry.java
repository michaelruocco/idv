package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import lombok.RequiredArgsConstructor;

import java.time.Duration;

@RequiredArgsConstructor
public class MobilePinsentry implements VerificationMethod {

    private static final String NAME = "mobile-pinsentry";
    private static final Duration DURATION = Duration.ofMinutes(5);

    private final Eligibility eligibility;
    private final PinsentryFunction function;

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

    public PinsentryFunction getFunction() {
        return function;
    }

}
