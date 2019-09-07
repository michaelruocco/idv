package uk.co.mruoc.verificationcontext.domain.method;

import lombok.ToString;

import java.time.Duration;

@ToString
public class FakeVerificationMethod implements VerificationMethod {

    private final Eligibility eligibility;

    public FakeVerificationMethod() {
        this(new Eligible());
    }

    public FakeVerificationMethod(final Eligibility eligibility) {
        this.eligibility = eligibility;
    }

    @Override
    public String getName() {
        return "fake-method";
    }

    @Override
    public Duration getDuration() {
        return Duration.ofMinutes(5);
    }

    @Override
    public Eligibility getEligibility() {
        return eligibility;
    }

}
