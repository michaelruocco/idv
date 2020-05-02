package uk.co.idv.domain.entities.lockout.policy;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.lockout.policy.state.ApplicableAttemptFilter;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.policy.PolicyRequest;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;
import uk.co.idv.domain.entities.policy.PolicyLevel;

import java.util.UUID;

@Slf4j
public class DefaultLockoutPolicy implements LockoutPolicy {

    private final UUID id;
    private final PolicyLevel level;
    private final LockoutStateCalculator stateCalculator;
    private final RecordAttemptStrategy recordAttemptStrategy;
    private final ApplicableAttemptFilter attemptFilter;

    @Builder
    public DefaultLockoutPolicy(final UUID id,
                                final PolicyLevel level,
                                final LockoutStateCalculator stateCalculator,
                                final RecordAttemptStrategy recordAttemptStrategy) {
        this(id, level, stateCalculator, recordAttemptStrategy, new ApplicableAttemptFilter(level));
    }

    public DefaultLockoutPolicy(final UUID id,
                                final PolicyLevel level,
                                final LockoutStateCalculator stateCalculator,
                                final RecordAttemptStrategy recordAttemptStrategy,
                                final ApplicableAttemptFilter attemptFilter) {
        this.id = id;
        this.stateCalculator = stateCalculator;
        this.level = level;
        this.recordAttemptStrategy = recordAttemptStrategy;
        this.attemptFilter = attemptFilter;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getType() {
        return stateCalculator.getType();
    }

    @Override
    public String getRecordAttemptStrategyType() {
        return recordAttemptStrategy.getType();
    }

    @Override
    public String getChannelId() {
        return level.getChannelId();
    }

    @Override
    public boolean isAliasLevel() {
        return level.isAliasLevel();
    }

    @Override
    public boolean appliesTo(final PolicyRequest request) {
        return level.appliesTo(request);
    }

    @Override
    public boolean shouldRecordAttempt(final RecordAttemptRequest request) {
        return recordAttemptStrategy.shouldRecordAttempt(request);
    }

    @Override
    public VerificationAttempts reset(final CalculateLockoutStateRequest request) {
        final VerificationAttempts attempts = request.getAttempts();
        final VerificationAttempts applicableAttempts = filterApplicableAttempts(request);
        return attempts.remove(applicableAttempts);
    }

    @Override
    public LockoutState calculateState(final CalculateLockoutStateRequest request) {
        final VerificationAttempts applicableAttempts = filterApplicableAttempts(request);
        final CalculateLockoutStateRequest updatedRequest = request.updateAttempts(applicableAttempts);
        return stateCalculator.calculate(updatedRequest);
    }

    @Override
    public LockoutStateCalculator getStateCalculator() {
        return stateCalculator;
    }

    @Override
    public PolicyLevel getLevel() {
        return level;
    }

    @Override
    public RecordAttemptStrategy getRecordAttemptStrategy() {
        return recordAttemptStrategy;
    }

    private VerificationAttempts filterApplicableAttempts(final CalculateLockoutStateRequest request) {
        return attemptFilter.filter(request);
    }

}
