package uk.co.idv.domain.entities.lockout.policy;


import uk.co.idv.domain.entities.policy.PolicyRequest;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;
import uk.co.idv.domain.entities.policy.PolicyLevel;

import java.util.UUID;

public interface LockoutPolicy {

    UUID getId();

    String getType();

    String getRecordAttemptStrategyType();

    String getChannelId();

    boolean isAliasLevel();

    boolean appliesTo(final PolicyRequest request);

    boolean shouldRecordAttempt(final RecordAttemptRequest request);

    VerificationAttempts reset(final VerificationAttempts attempts, final LockoutRequest request);

    VerificationAttempts filterApplicableAttempts(final VerificationAttempts attempts, final LockoutRequest request);

    LockoutStateCalculator getStateCalculator();

    RecordAttemptStrategy getRecordAttemptStrategy();

    PolicyLevel getLevel();

}
