package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import java.util.Optional;

public interface Eligibility {

    boolean isEligible();

    Optional<String> getReason();

}
