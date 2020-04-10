package uk.co.idv.domain.entities.verificationcontext.method.eligibility;

import java.util.Optional;

public interface Eligibility {

    boolean isEligible();

    Optional<String> getReason();

}
