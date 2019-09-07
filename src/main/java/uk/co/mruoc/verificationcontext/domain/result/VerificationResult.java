package uk.co.mruoc.verificationcontext.domain.result;

import java.time.Instant;
import java.util.UUID;

public interface VerificationResult {

    String getMethodName();

    boolean isSuccessful();

    UUID getVerificationId();

    Instant getTimestamp();

}
