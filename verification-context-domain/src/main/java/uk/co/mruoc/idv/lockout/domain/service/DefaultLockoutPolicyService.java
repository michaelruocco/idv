package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.lockout.dao.LockoutPolicyDao;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

@Builder
public class DefaultLockoutPolicyService implements LockoutPolicyService {

    private final LockoutPolicyDao dao;
    private final LockoutPolicyFactory policyFactory;

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

    @Override
    public void addPolicy(final LockoutPolicyParameters parameters) {
        final LockoutPolicy policy = policyFactory.build(parameters);
        addPolicy(policy);
    }

    @Override
    public void addPolicy(LockoutPolicy policy) {
        dao.save(policy);
    }

    private LockoutPolicy load(final LockoutRequest request) {
        return dao.load(request)
                .orElseThrow(() -> new LockoutPolicyNotFoundException(request));
    }

}
