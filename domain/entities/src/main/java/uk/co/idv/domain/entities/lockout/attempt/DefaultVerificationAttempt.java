package uk.co.idv.domain.entities.lockout.attempt;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import uk.co.idv.domain.entities.identity.alias.Alias;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@ToString
public class DefaultVerificationAttempt implements VerificationAttempt {

    private final UUID contextId;
    private final String channelId;
    private final String activityName;
    private final Alias alias;
    private final UUID idvIdValue;
    private final String methodName;
    private final UUID verificationId;
    private final Instant timestamp;
    private final boolean successful;

    @Override
    public String getAliasType() {
        return alias.getType();
    }

    public static class DefaultVerificationAttemptBuilder {

        public VerificationAttempt buildSuccessful() {
            successful(true);
            return build();
        }

        public VerificationAttempt buildFailed() {
            successful(false);
            return build();
        }

    }

}
