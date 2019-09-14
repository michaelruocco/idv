package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import java.time.Duration;

public class CardCredentials implements VerificationMethod {

    private static final String NAME = "card-credentials";
    private static final Duration DURATION = Duration.ofMinutes(5);

    private final Eligibility eligibility;

    public CardCredentials(final Eligibility eligibility) {
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
