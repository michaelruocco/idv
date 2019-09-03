package uk.co.mruoc.verificationcontext.domain.method;

import java.util.Optional;

public class Ineligible implements Eligibility {

    private final String reason;

    public Ineligible(final String reason) {
        this.reason = reason;
    }

    @Override
    public boolean isEligible() {
        return false;
    }

    @Override
    public Optional<String> reason() {
        return Optional.of(reason);
    }

}
