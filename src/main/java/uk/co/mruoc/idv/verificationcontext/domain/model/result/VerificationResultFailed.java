package uk.co.mruoc.idv.verificationcontext.domain.model.result;

import java.time.Instant;
import java.util.UUID;

public class VerificationResultFailed extends AbstractVerificationResult {

    private static final boolean SUCCESSFUL = false;

    public VerificationResultFailed(final String name,
                                    final UUID verificationId,
                                    final Instant timestamp) {
        super(name, verificationId, timestamp, SUCCESSFUL);
    }

}
