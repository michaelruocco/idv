package uk.co.idv.domain.usecases.lockout;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;

import java.util.Collection;

@RequiredArgsConstructor
public class DefaultLockoutPolicyService implements LockoutPolicyService {

    private final LockoutPolicyDao dao;

    @Override
    public boolean shouldRecordAttempt(final RecordAttemptRequest request) {
        final LockoutPolicy policy = load(request);
        return policy.shouldRecordAttempt(request);
    }

    @Override
    public LockoutState calculateState(final CalculateLockoutStateRequest request) {
        final LockoutPolicy policy = load(request);
        final VerificationAttempts applicableAttempts = policy.filterApplicableAttempts(request.getAttempts(), request);
        final CalculateLockoutStateRequest updatedRequest = request.updateAttempts(applicableAttempts);
        final LockoutStateCalculator stateCalculator = policy.getStateCalculator();
        return stateCalculator.calculate(updatedRequest);
    }

    @Override
    public VerificationAttempts resetAttempts(final CalculateLockoutStateRequest request) {
        final LockoutPolicy policy = load(request);
        return policy.reset(request.getAttempts(), request);
    }

    @Override
    public void savePolicy(final LockoutPolicy policy) {
        // TODO add logic here to error if policy for same set of parameters already exists
        // TODO also need to add update method to this class
        dao.save(policy);
    }

    @Override
    public Collection<LockoutPolicy> loadPolicies() {
        return dao.load();
    }

    private LockoutPolicy load(final LockoutRequest request) {
        return dao.load(request)
                .orElseThrow(() -> new LockoutPolicyNotFoundException(request));
    }

}
