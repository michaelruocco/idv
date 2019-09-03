package uk.co.mruoc.verificationcontext.domain.method;

import java.util.Optional;

public interface Eligibility {

    boolean isEligible();

    Optional<String> reason();

}
