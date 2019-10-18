package uk.co.mruoc.idv.verificationcontext.domain.model.result;

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
