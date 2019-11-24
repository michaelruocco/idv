package uk.co.idv.domain.usecases.lockout;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.LockoutPolicyRequest;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevelConverter;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DefaultLockoutPolicyService implements LockoutPolicyService {

    private final LockoutPolicyDao dao;
    private final LockoutLevelConverter lockoutLevelConverter;

    public DefaultLockoutPolicyService(final LockoutPolicyDao dao) {
        this(dao, new LockoutLevelConverter());
    }

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
    public void createPolicy(final LockoutPolicy policy) {
        final Collection<LockoutPolicy> existingPolicies = loadPolicies(policy.getLockoutLevel());
        if (!existingPolicies.isEmpty()) {
            throw new LockoutPoliciesAlreadyExistException(existingPolicies);
        }
        dao.save(policy);
    }

    @Override
    public void updatePolicy(final LockoutPolicy policy) {
        final UUID id = policy.getId();
        final Optional<LockoutPolicy> loadedPolicy = dao.load(id);
        if (loadedPolicy.isEmpty()) {
            throw new LockoutPolicyNotFoundException(id);
        }
        dao.save(policy);
    }

    @Override
    public Collection<LockoutPolicy> loadPolicies() {
        return dao.load();
    }

    private Collection<LockoutPolicy> loadPolicies(final LockoutLevel level) {
        final Collection<LockoutPolicyRequest> requests = lockoutLevelConverter.toPolicyRequests(level);
        return requests.stream()
                .map(dao::load)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    private LockoutPolicy load(final LockoutRequest request) {
        return dao.load(request)
                .orElseThrow(() -> new RequestedLockoutPolicyNotFoundException(request));
    }

}
