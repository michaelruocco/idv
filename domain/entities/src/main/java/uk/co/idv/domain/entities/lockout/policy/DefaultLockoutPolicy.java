package uk.co.idv.domain.entities.lockout.policy;

import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;

import java.util.UUID;

public class DefaultLockoutPolicy implements LockoutPolicy {

    private final UUID id;
    private final LockoutStateCalculator stateCalculator;
    private final LockoutLevel level;
    private final RecordAttemptStrategy recordAttemptStrategy;

    public DefaultLockoutPolicy(final UUID id,
                                final LockoutStateCalculator stateCalculator,
                                final LockoutLevel level,
                                final RecordAttemptStrategy recordAttemptStrategy) {
        this.id = id;
        this.stateCalculator = stateCalculator;
        this.level = level;
        this.recordAttemptStrategy = recordAttemptStrategy;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getLockoutType() {
        return stateCalculator.getType();
    }

    @Override
    public String getLockoutLevelType() {
        return level.getType();
    }

    @Override
    public String getRecordAttemptStrategyType() {
        return recordAttemptStrategy.getType();
    }

    @Override
    public boolean appliesTo(final LockoutRequest request) {
        return level.appliesTo(request);
    }

    @Override
    public boolean shouldRecordAttempt(final RecordAttemptRequest request) {
        return recordAttemptStrategy.shouldRecordAttempt(request);
    }

    @Override
    public VerificationAttempts reset(final VerificationAttempts attempts, final LockoutRequest request) {
        final VerificationAttempts applicableAttempts = filterApplicableAttempts(attempts, request);
        return attempts.remove(applicableAttempts);
    }

    @Override
    public VerificationAttempts filterApplicableAttempts(final VerificationAttempts attempts,
                                                         final LockoutRequest request) {
        if (level.isAliasLevel()) {
            final VerificationAttempts aliasAttempts = attempts.filterMatching(request.getAlias());
            return aliasAttempts.filterMatching(level);
        }
        return attempts.filterMatching(level);
    }

    @Override
    public LockoutStateCalculator getStateCalculator() {
        return stateCalculator;
    }

    /*@Override
    public LockoutState reset(final CalculateLockoutStateRequest request) {
        final VerificationAttempts resetAttempts = removeApplicableAttempts(request);
        final CalculateLockoutStateRequest calculateRequest = requestConverter.toCalculateRequest(request, resetAttempts);
        return stateCalculator.calculate(calculateRequest);
    }

    @Override
    public LockoutState calculateLockoutState(final CalculateLockoutStateRequest request) {
        final VerificationAttempts applicableAttempts = filterApplicableAttempts(request);
        final CalculateLockoutStateRequest calculateRequest = requestConverter.toCalculateRequest(request, applicableAttempts);
        return stateCalculator.calculate(calculateRequest);
    }*/

    @Override
    public LockoutLevel getLockoutLevel() {
        return level;
    }

    @Override
    public RecordAttemptStrategy getRecordAttemptStrategy() {
        return recordAttemptStrategy;
    }

}
