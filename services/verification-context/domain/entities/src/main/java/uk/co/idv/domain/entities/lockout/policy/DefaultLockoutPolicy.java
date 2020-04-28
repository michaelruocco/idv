package uk.co.idv.domain.entities.lockout.policy;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.policy.PolicyRequest;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;
import uk.co.idv.domain.entities.policy.PolicyLevel;

import java.util.UUID;

@Slf4j
@Builder
public class DefaultLockoutPolicy implements LockoutPolicy {

    private final UUID id;
    private final LockoutStateCalculator stateCalculator;
    private final PolicyLevel level;
    private final RecordAttemptStrategy recordAttemptStrategy;

    public DefaultLockoutPolicy(final UUID id,
                                final LockoutStateCalculator stateCalculator,
                                final PolicyLevel level,
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
    public VerificationAttempts reset(final VerificationAttempts attempts, final LockoutRequest request) {
        final VerificationAttempts applicableAttempts = filterApplicableAttempts(attempts, request);
        return attempts.remove(applicableAttempts);
    }

    @Override
    public VerificationAttempts filterApplicableAttempts(final VerificationAttempts attempts,
                                                         final LockoutRequest request) {
        log.info("level {} is alias level {}", level, level.isAliasLevel());
        if (level.isAliasLevel()) {
            log.info("filtering by alias {}", request.getAlias());
            final VerificationAttempts aliasAttempts = attempts.filterMatching(request.getAlias());
            return aliasAttempts.filterMatching(level);
        }
        return attempts.filterMatching(level);
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

}
