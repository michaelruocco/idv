package uk.co.mruoc.verificationcontext.domain.method;

import lombok.RequiredArgsConstructor;

import java.time.Duration;

@RequiredArgsConstructor
public class MobilePinsentry implements VerificationMethod {

    private final Eligibility eligibility;
    private final PinsentryFunction function;

    @Override
    public String getName() {
        return Names.MOBILE_PINSENTRY;
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

    public PinsentryFunction getFunction() {
        return function;
    }

}
