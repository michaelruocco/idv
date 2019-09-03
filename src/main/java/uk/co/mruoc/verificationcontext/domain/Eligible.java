package uk.co.mruoc.verificationcontext.domain;

import java.util.Optional;

public class Eligible implements Eligibility {

    @Override
    public boolean isEligible() {
        return true;
    }

    @Override
    public Optional<String> reason() {
        return Optional.empty();
    }

}
