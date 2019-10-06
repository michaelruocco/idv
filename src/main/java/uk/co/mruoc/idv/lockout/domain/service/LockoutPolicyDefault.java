package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.util.function.Predicate;

public class LockoutPolicyDefault implements LockoutPolicy {

    private final Predicate<LockoutRequest> appliesToPolicy;
    private final LockoutStateCalculator stateCalculator;
    private final RecordAttemptStrategy recordAttemptStrategy;

    public LockoutPolicyDefault(final Predicate<LockoutRequest> appliesToPolicy,
                                final LockoutStateCalculator stateCalculator,
                                final RecordAttemptStrategy recordAttemptStrategy) {
        this.appliesToPolicy = appliesToPolicy;
        this.stateCalculator = stateCalculator;
        this.recordAttemptStrategy = recordAttemptStrategy;
    }

    @Override
    public boolean appliesTo(final LockoutRequest request) {
        return appliesToPolicy.test(request);
    }

    @Override
    public boolean shouldRecordAttempt(final RecordAttemptRequest request) {
        return recordAttemptStrategy.shouldRecordAttempt(request);
    }

    @Override
    public LockoutState reset(final CalculateLockoutStateRequest request) {
        final VerificationAttempts attempts = request.getAttempts();
        final VerificationAttempts resetAttempts = attempts.resetBy(appliesToPolicy);
        return stateCalculator.calculate(request.withAttempts(resetAttempts));
    }

    @Override
    public LockoutState calculateLockoutState(final CalculateLockoutStateRequest request) {
        final VerificationAttempts applicableAttempts = filterApplicableAttempts(request.getAttempts());
        return stateCalculator.calculate(request.withAttempts(applicableAttempts));
    }

    private VerificationAttempts filterApplicableAttempts(final VerificationAttempts attempts) {
        return attempts.filterBy(appliesToPolicy);
    }

}
