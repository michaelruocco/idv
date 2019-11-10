package uk.co.idv.domain.usecases.lockout;

import lombok.Getter;
import uk.co.idv.domain.entities.lockout.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.entities.lockout.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

import java.util.Collection;

public interface LockoutPolicyService {

    boolean shouldRecordAttempt(RecordAttemptRequest request);

    LockoutState calculateState(CalculateLockoutStateRequest request);

    VerificationAttempts resetAttempts(CalculateLockoutStateRequest request);

    default void addPolicies(Collection<LockoutPolicyParameters> policies) {
        policies.forEach(this::addPolicy);
    }

    void addPolicy(LockoutPolicyParameters policy);

    Collection<LockoutPolicyParameters> loadPolicies();

    @Getter
    class LockoutPolicyNotFoundException extends RuntimeException {

        private final LockoutRequest request;

        public LockoutPolicyNotFoundException(final LockoutRequest request) {
            super(String.format("channelId %s activity %s aliasType %s",
                    request.getChannelId(),
                    request.getActivityName(),
                    request.getAliasType()));
            this.request = request;
        }

    }

}
