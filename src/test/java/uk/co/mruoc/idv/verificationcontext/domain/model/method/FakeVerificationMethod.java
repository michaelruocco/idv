package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import lombok.ToString;

import java.time.Duration;

@ToString
public class FakeVerificationMethod implements VerificationMethod {

    private static final String DEFAULT_NAME = "fake-method";
    private static final Eligibility DEFAULT_ELIGIBILITY = new Eligible();
    private static final Duration DEFAULT_DURATION = Duration.ofMinutes(5);

    private final String name;
    private final Eligibility eligibility;
    private final Duration duration;

    public FakeVerificationMethod() {
        this(DEFAULT_NAME, DEFAULT_ELIGIBILITY, DEFAULT_DURATION);
    }

    public FakeVerificationMethod(final String name) {
        this(name, DEFAULT_ELIGIBILITY, DEFAULT_DURATION);
    }

    public FakeVerificationMethod(final Duration duration) {
        this(DEFAULT_NAME, duration);
    }

    public FakeVerificationMethod(final Eligibility eligibility) {
        this(DEFAULT_NAME, eligibility);
    }

    public FakeVerificationMethod(final String name, final Duration duration) {
        this(name, DEFAULT_ELIGIBILITY, duration);
    }

    public FakeVerificationMethod(final String name, final Eligibility eligibility) {
        this(name, eligibility, DEFAULT_DURATION);
    }

    public FakeVerificationMethod(final String name, final Eligibility eligibility, final Duration duration) {
        this.name = name;
        this.eligibility = eligibility;
        this.duration = duration;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Duration getDuration() {
        return  duration;
    }

    @Override
    public Eligibility getEligibility() {
        return eligibility;
    }

}
