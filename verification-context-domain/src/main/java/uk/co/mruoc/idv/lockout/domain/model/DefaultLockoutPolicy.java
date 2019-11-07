package uk.co.mruoc.idv.lockout.domain.model;

import lombok.Builder;
import uk.co.mruoc.idv.lockout.domain.service.CalculateLockoutStateRequest;
import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateRequestConverter;
import uk.co.mruoc.idv.lockout.domain.service.RecordAttemptRequest;

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
        if (parameters.useAliasLevelLocking()) {
            final VerificationAttempts aliasAttempts = attempts.filterMatching(request.getAlias());
            return aliasAttempts.filterMatching(parameters);
        }
        return attempts.filterMatching(parameters);
    }

}
