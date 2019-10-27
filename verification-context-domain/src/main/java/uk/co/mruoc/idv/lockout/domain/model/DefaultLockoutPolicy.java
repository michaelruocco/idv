package uk.co.mruoc.idv.lockout.domain.model;

import lombok.Builder;
import uk.co.mruoc.idv.lockout.domain.service.CalculateLockoutStateRequest;
import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateRequestConverter;
import uk.co.mruoc.idv.lockout.domain.service.RecordAttemptRequest;

import java.util.function.Predicate;

public class DefaultLockoutPolicy implements LockoutPolicy {

    private final LockoutPolicyParameters parameters;
    private final Predicate<LockoutRequest> appliesToPolicy;
    private final LockoutStateCalculator stateCalculator;
    private final RecordAttemptStrategy recordAttemptStrategy;
    private final LockoutStateRequestConverter requestConverter;

    @Builder
    public DefaultLockoutPolicy(final LockoutPolicyParameters parameters,
                                final Predicate<LockoutRequest> appliesToPolicy,
                                final LockoutStateCalculator stateCalculator,
                                final RecordAttemptStrategy recordAttemptStrategy) {
        this(parameters,
                appliesToPolicy,
                stateCalculator,
                recordAttemptStrategy,
                new LockoutStateRequestConverter()
        );
    }

    public DefaultLockoutPolicy(final LockoutPolicyParameters parameters,
                                final Predicate<LockoutRequest> appliesToPolicy,
                                final LockoutStateCalculator stateCalculator,
                                final RecordAttemptStrategy recordAttemptStrategy,
                                final LockoutStateRequestConverter requestConverter) {
        this.parameters = parameters;
        this.appliesToPolicy = appliesToPolicy;
        this.stateCalculator = stateCalculator;
        this.recordAttemptStrategy = recordAttemptStrategy;
        this.requestConverter = requestConverter;
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
    public VerificationAttempts reset(final VerificationAttempts attempts) {
        return attempts.resetBy(appliesToPolicy);
    }

    @Override
    public LockoutState reset(final CalculateLockoutStateRequest request) {
        final VerificationAttempts attempts = request.getAttempts();
        final VerificationAttempts resetAttempts = reset(attempts);
        final CalculateLockoutStateRequest calculateRequest = requestConverter.toCalculateRequest(request, resetAttempts);
        return stateCalculator.calculate(calculateRequest);
    }

    @Override
    public LockoutState calculateLockoutState(final CalculateLockoutStateRequest request) {
        final VerificationAttempts applicableAttempts = filterApplicableAttempts(request.getAttempts());
        final CalculateLockoutStateRequest calculateRequest = requestConverter.toCalculateRequest(request, applicableAttempts);
        return stateCalculator.calculate(calculateRequest);
    }

    @Override
    public LockoutPolicyParameters getParameters() {
        return parameters;
    }

    private VerificationAttempts filterApplicableAttempts(final VerificationAttempts attempts) {
        return attempts.filterBy(appliesToPolicy);
    }

}
