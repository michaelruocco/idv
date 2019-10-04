package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.util.function.Predicate;

public class LockoutPolicyDefault implements LockoutPolicy {

    private final Predicate<LockoutPolicyRequest> appliesToPolicy;
    private final LockoutStateCalculator stateCalculator;
    private final RecordAttemptStrategy recordAttemptStrategy;

    public LockoutPolicyDefault(final Predicate<LockoutPolicyRequest> appliesToPolicy,
                                final LockoutStateCalculator stateCalculator,
                                final RecordAttemptStrategy recordAttemptStrategy) {
        this.appliesToPolicy = appliesToPolicy;
        this.stateCalculator = stateCalculator;
        this.recordAttemptStrategy = recordAttemptStrategy;
    }

    @Override
    public boolean appliesTo(final LockoutPolicyRequest request) {
        return appliesToPolicy.test(request);
    }

    @Override
    public boolean shouldRecordAttempt(final RecordAttemptRequest request) {
        return recordAttemptStrategy.shouldRecordAttempt(request);
    }

    @Override
    public VerificationAttempts reset(final ResetAttemptsRequest request) {
        final VerificationAttempts attempts = request.getAttempts();
        return attempts.resetBy(appliesToPolicy);
    }

    @Override
    public LockoutState calculateLockoutState(final CalculateLockoutStateRequest request) {
        final VerificationAttempts applicableAttempts = filterApplicableAttempts(request.getAttempts());
        return stateCalculator.calculate(request.updateAttempts(applicableAttempts));
    }

    private VerificationAttempts filterApplicableAttempts(final VerificationAttempts attempts) {
        return attempts.filterBy(appliesToPolicy);
    }

}
