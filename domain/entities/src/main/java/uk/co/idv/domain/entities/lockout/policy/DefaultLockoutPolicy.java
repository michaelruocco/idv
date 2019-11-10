package uk.co.idv.domain.entities.lockout.policy;

import lombok.Builder;
import uk.co.idv.domain.entities.lockout.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.entities.lockout.state.LockoutState;
import uk.co.idv.domain.entities.lockout.state.LockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.state.LockoutStateRequestConverter;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

public class DefaultLockoutPolicy implements LockoutPolicy {

    private final LockoutPolicyParameters parameters;
    private final LockoutStateCalculator stateCalculator;
    private final RecordAttemptStrategy recordAttemptStrategy;
    private final LockoutStateRequestConverter requestConverter;

    @Builder
    public DefaultLockoutPolicy(final LockoutPolicyParameters parameters,
                                final LockoutStateCalculator stateCalculator,
                                final RecordAttemptStrategy recordAttemptStrategy) {
        this(parameters,
                stateCalculator,
                recordAttemptStrategy,
                new LockoutStateRequestConverter()
        );
    }

    public DefaultLockoutPolicy(final LockoutPolicyParameters parameters,
                                final LockoutStateCalculator stateCalculator,
                                final RecordAttemptStrategy recordAttemptStrategy,
                                final LockoutStateRequestConverter requestConverter) {
        this.parameters = parameters;
        this.stateCalculator = stateCalculator;
        this.recordAttemptStrategy = recordAttemptStrategy;
        this.requestConverter = requestConverter;
    }

    @Override
    public boolean appliesTo(final LockoutRequest request) {
        return parameters.appliesTo(request);
    }

    @Override
    public boolean shouldRecordAttempt(final RecordAttemptRequest request) {
        return recordAttemptStrategy.shouldRecordAttempt(request);
    }

    @Override
    public VerificationAttempts reset(final VerificationAttempts attempts, final LockoutRequest request) {
        return removeApplicableAttempts(attempts, request);
    }

    @Override
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
    }

    @Override
    public LockoutPolicyParameters getParameters() {
        return parameters;
    }

    @Override
    public LockoutStateCalculator getStateCalculator() {
        return stateCalculator;
    }

    @Override
    public RecordAttemptStrategy getRecordAttemptStrategy() {
        return recordAttemptStrategy;
    }

    private VerificationAttempts removeApplicableAttempts(final CalculateLockoutStateRequest request) {
        return removeApplicableAttempts(request.getAttempts(), request);
    }

    private VerificationAttempts removeApplicableAttempts(final VerificationAttempts attempts,
                                                          final LockoutRequest request) {
        final VerificationAttempts applicableAttempts = filterApplicableAttempts(attempts, request);
        return attempts.remove(applicableAttempts);
    }

    private VerificationAttempts filterApplicableAttempts(final CalculateLockoutStateRequest request) {
        return filterApplicableAttempts(request.getAttempts(), request);
    }

    private VerificationAttempts filterApplicableAttempts(final VerificationAttempts attempts,
                                                          final LockoutRequest request) {
        if (parameters.isAliasLevel()) {
            final VerificationAttempts aliasAttempts = attempts.filterMatching(request.getAlias());
            return aliasAttempts.filterMatching(parameters);
        }
        return attempts.filterMatching(parameters);
    }

}
