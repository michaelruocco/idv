package uk.co.idv.domain.entities.lockout;

import lombok.Builder;
import uk.co.idv.domain.entities.identity.Alias;

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
