package uk.co.idv.uk.domain.entities.lockout.rsa;

import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;

import java.util.UUID;

public class RsaLockoutPolicy extends DefaultLockoutPolicy {

    public RsaLockoutPolicy(final UUID id) {
        super(id,
                new RsaLockoutStateCalculator(),
                new RsaLockoutPolicyLevel(),
                new RecordEveryAttempt()
        );
    }

}
