package uk.co.mruoc.verificationcontext.domain.result;

import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class VerificationResultSuccessful implements VerificationResult {

    private final String name;
    private final UUID verificationId;
    private final Instant timestamp;

    @Override
    public String getMethodName() {
        return name;
    }

    @Override
    public UUID getVerificationId() {
        return verificationId;
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean isSuccessful() {
        return true;
    }

}
