package uk.co.idv.domain.usecases.lockout;

import lombok.Builder;
import uk.co.idv.domain.entities.lockout.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.entities.lockout.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

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
        return policy.reset(request.getAttempts(), request);
    }

    @Override
    public void addPolicy(final LockoutPolicyParameters parameters) {
        final LockoutPolicy policy = parametersConverter.toPolicy(parameters);
        // TODO add logic here to error if policy for same set of parameters already exists
        // TODO also need to add update method to this class
        dao.save(policy);
    }

    @Override
    public Collection<LockoutPolicyParameters> loadPolicies() {
        return dao.load().stream()
                .map(LockoutPolicy::getParameters)
                .collect(Collectors.toList());
    }

    private LockoutPolicy load(final LockoutRequest request) {
        return dao.load(request)
                .orElseThrow(() -> new LockoutPolicyNotFoundException(request));
    }

}
