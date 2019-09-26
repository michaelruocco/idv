package uk.co.mruoc.idv.verificationcontext.domain.model.result;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

public class VerificationResultFailed extends AbstractVerificationResult {

    private static final boolean FAILED = false;

    @Builder
    public VerificationResultFailed(final String methodName,
                                    final UUID verificationId,
                                    final Instant timestamp) {
        super(methodName, verificationId, timestamp, FAILED);
    }

}
