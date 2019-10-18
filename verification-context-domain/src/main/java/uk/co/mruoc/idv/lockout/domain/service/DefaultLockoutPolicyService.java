package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.util.Arrays;
import java.util.Collection;

public class DefaultLockoutPolicyService implements LockoutPolicyService {

    private final Collection<LockoutPolicy> policies;

    public DefaultLockoutPolicyService(final LockoutPolicy... policies) {
        this(Arrays.asList(policies));
    }

    public DefaultLockoutPolicyService(final Collection<LockoutPolicy> policies) {
        this.policies = policies;
    }

    @Override
    public boolean shouldRecordAttempt(final RecordAttemptRequest request) {
        final LockoutPolicy policy = load(request);
        return policy.shouldRecordAttempt(request);
    }

    @Override
    public LockoutState calculateState(final CalculateLockoutStateRequest request) {
        final LockoutPolicy policy = load(request);
        return policy.calculateLockoutState(request);
    }

    @Override
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

}
