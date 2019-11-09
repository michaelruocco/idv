package uk.co.idv.domain.entities.verificationcontext.result;

import java.time.Instant;
import java.util.UUID;

public interface VerificationResult {

    String getMethodName();

    boolean isSuccessful();

    UUID getVerificationId();

    Instant getTimestamp();

    default boolean hasMethodName(final String otherName) {
        return getMethodName().equals(otherName);
    }

}
