package uk.co.idv.domain.entities.verificationcontext.method;

import java.util.Optional;

public interface Eligibility {

    boolean isEligible();

    Optional<String> getReason();

}
