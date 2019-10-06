package uk.co.mruoc.idv.lockout.domain.model;

import lombok.Builder;
import uk.co.mruoc.idv.identity.domain.model.Alias;

import java.time.Instant;
import java.util.UUID;

public class VerificationAttemptSuccessful extends AbstractVerificationAttempt {

    private static final boolean SUCCESSFUL = true;

    @Builder
    public VerificationAttemptSuccessful(final UUID contextId,
                                        final String channelId,
                                        final String activityName,
                                        final Alias alias,
                                        final UUID idvIdValue,
                                        final String methodName,
                                        final UUID verificationId,
                                        final Instant timestamp) {
        super(contextId,
                channelId,
                activityName,
                alias,
                idvIdValue,
                methodName,
                verificationId,
                timestamp,
                SUCCESSFUL
        );
    }

}
