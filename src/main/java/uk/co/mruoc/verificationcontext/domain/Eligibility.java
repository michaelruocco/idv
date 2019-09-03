package uk.co.mruoc.verificationcontext.domain;

import java.util.Optional;

public interface Eligibility {

    boolean isEligible();

    Optional<String> reason();

}
