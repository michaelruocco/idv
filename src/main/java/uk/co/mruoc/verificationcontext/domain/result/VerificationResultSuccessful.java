package uk.co.mruoc.verificationcontext.domain.result;

import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@ToString
public class VerificationResultSuccessful extends AbstractVerificationResult {

    private static final boolean SUCCESSFUL = true;

    public VerificationResultSuccessful(final String name,
                                        final UUID verificationId,
                                        final Instant timestamp) {
        super(name, verificationId, timestamp, SUCCESSFUL);
    }

}
