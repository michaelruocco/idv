package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Getter;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

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
