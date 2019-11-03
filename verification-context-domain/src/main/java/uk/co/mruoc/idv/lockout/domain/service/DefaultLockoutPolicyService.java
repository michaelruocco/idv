package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.lockout.dao.LockoutPolicyDao;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicy;
import uk.co.mruoc.idv.lockout.domain.model.AbstractLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.util.Collection;
import java.util.stream.Collectors;

@Builder
public class DefaultLockoutPolicyService implements LockoutPolicyService {

    private final LockoutPolicyDao dao;
    private final LockoutPolicyParametersConverter parametersConverter;

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
    public void addPolicy(final AbstractLockoutPolicyParameters parameters) {
        final LockoutPolicy policy = parametersConverter.toPolicy(parameters);
        dao.save(policy);
    }

    @Override
    public Collection<AbstractLockoutPolicyParameters> loadPolicies() {
        return dao.load().stream()
                .map(LockoutPolicy::getParameters)
                .collect(Collectors.toList());
    }

    private LockoutPolicy load(final LockoutRequest request) {
        return dao.load(request)
                .orElseThrow(() -> new LockoutPolicyNotFoundException(request));
    }

}
