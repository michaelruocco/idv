package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Getter;
import uk.co.mruoc.idv.lockout.domain.model.DefaultLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.util.Collection;

public interface LockoutPolicyService {

    boolean shouldRecordAttempt(RecordAttemptRequest request);

    LockoutState calculateState(CalculateLockoutStateRequest request);

    VerificationAttempts resetAttempts(CalculateLockoutStateRequest request);

    void addPolicy(DefaultLockoutPolicyParameters policy);

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
