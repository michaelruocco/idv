package uk.co.idv.domain.entities.verificationcontext.method;

import lombok.EqualsAndHashCode;

import java.util.Optional;

@EqualsAndHashCode
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
    public Optional<String> getReason() {
        return Optional.of(reason);
    }

}
