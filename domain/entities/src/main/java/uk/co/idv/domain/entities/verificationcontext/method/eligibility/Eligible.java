package uk.co.idv.domain.entities.verificationcontext.method.eligibility;

import lombok.EqualsAndHashCode;

import java.util.Optional;

@EqualsAndHashCode
public class Eligible implements Eligibility {

    @Override
    public boolean isEligible() {
        return true;
    }

    @Override
    public Optional<String> getReason() {
        return Optional.empty();
    }

}
