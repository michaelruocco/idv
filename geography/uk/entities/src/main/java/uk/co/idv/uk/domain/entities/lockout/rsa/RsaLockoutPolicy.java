package uk.co.idv.uk.domain.entities.lockout.rsa;

import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;

import java.util.UUID;

public class RsaLockoutPolicy extends HardLockoutPolicy {

    public RsaLockoutPolicy(final UUID id) {
        super(id,
                new RsaLockoutLevel(),
                new RecordEveryAttempt(),
                new RsaLockoutStateCalculator()
        );
    }

}
