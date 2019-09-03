package uk.co.mruoc.verificationcontext.domain.method;

import java.time.Duration;

public class CardCredentials implements VerificationMethod {

    private final Eligibility eligibility;

    public CardCredentials(final Eligibility eligibility) {
        this.eligibility = eligibility;
    }

    @Override
    public String getName() {
        return Names.CARD_CREDENTIALS;
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
