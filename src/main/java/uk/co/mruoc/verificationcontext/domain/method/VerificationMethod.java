package uk.co.mruoc.verificationcontext.domain.method;

import java.time.Duration;

public interface VerificationMethod {

    String getName();

    Duration getDuration();

    Eligibility getEligibility();

    default boolean isEligible() {
        return getEligibility().isEligible();
    }

    default boolean hasName(final String otherName) {
        return getName().equals(otherName);
    }

}
