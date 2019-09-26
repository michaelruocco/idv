package uk.co.mruoc.idv.lockout.domain;

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
                                        final Alias providedAlias,
                                        final UUID idvId,
                                        final String methodName,
                                        final UUID verificationId,
                                        final Instant timestamp) {
        super(contextId,
                channelId,
                activityName,
                providedAlias,
                idvId,
                methodName,
                verificationId,
                timestamp,
                SUCCESSFUL
        );
    }

}
