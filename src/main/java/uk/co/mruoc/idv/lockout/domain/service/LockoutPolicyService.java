package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Getter;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.util.Collection;
import java.util.Collections;

public class LockoutPolicyService {

    private final Collection<LockoutPolicy> policies;
    private final LockoutStateRequestConverter requestConverter;

    public LockoutPolicyService() {
        this(new LockoutStateRequestConverter());
    }

    public LockoutPolicyService(final LockoutStateRequestConverter requestConverter) {
        this(Collections.singleton(new LockoutPolicyRsa()), requestConverter);
    }

    public LockoutPolicyService(final Collection<LockoutPolicy> policies,
                                final LockoutStateRequestConverter requestConverter) {
        this.policies = policies;
        this.requestConverter = requestConverter;
    }

    public boolean shouldRecordAttempt(final RecordAttemptRequest request) {
        final LockoutPolicy policy = load(request);
        return policy.shouldRecordAttempt(request);
    }

    public LockoutState calculateState(final CalculateLockoutStateRequest request) {
        final LockoutPolicy policy = load(request);
        return policy.calculateLockoutState(request);
    }

    public VerificationAttempts resetAttempts(final CalculateLockoutStateRequest request) {
        final LockoutPolicy policy = load(request);
        return policy.reset(request.getAttempts());
    }

    private LockoutPolicy load(final LockoutRequest request) {
        return policies.stream()
                .filter(policy -> policy.appliesTo(request))
                .findFirst()
                .orElseThrow(() -> new LockoutPolicyNotFoundException(request));
    }

    @Getter
    public static class LockoutPolicyNotFoundException extends RuntimeException {

        private final LockoutRequest request;

        public LockoutPolicyNotFoundException(final LockoutRequest request) {
            super(String.format("channelId %s, activity %s aliasType %s",
                    request.getChannelId(),
                    request.getActivityName(),
                    request.getAliasType()));
            this.request = request;
        }

    }

}
