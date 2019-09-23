package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import java.time.Duration;
import java.util.Optional;

public interface VerificationMethod {

    String getName();

    Duration getDuration();

    Eligibility getEligibility();

    default boolean isEligible() {
        return getEligibility().isEligible();
    }

    default Optional<String> getEligibilityReason() {
        return getEligibility().getReason();
    }

    default boolean hasName(final String otherName) {
        return getName().equals(otherName);
    }

}
