package uk.co.idv.uk.domain.entities.lockout;

import uk.co.idv.domain.entities.lockout.policy.maxattempts.MaxAttemptsLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;

import java.util.UUID;

public class RsaLockoutPolicy extends MaxAttemptsLockoutPolicy {

    public RsaLockoutPolicy(final UUID id, final String aliasType) {
        super(id,
                new RsaLockoutLevel(aliasType),
                new RecordEveryAttempt(),
                new RsaLockoutStateCalculator()
        );
    }

}
