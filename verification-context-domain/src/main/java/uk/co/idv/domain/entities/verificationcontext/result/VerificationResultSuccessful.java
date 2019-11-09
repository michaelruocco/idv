package uk.co.idv.domain.entities.verificationcontext.result;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

public class VerificationResultSuccessful extends AbstractVerificationResult {

    private static final boolean SUCCESSFUL = true;

    @Builder
    public VerificationResultSuccessful(final String methodName,
                                        final UUID verificationId,
                                        final Instant timestamp) {
        super(methodName, verificationId, timestamp, SUCCESSFUL);
    }

}
