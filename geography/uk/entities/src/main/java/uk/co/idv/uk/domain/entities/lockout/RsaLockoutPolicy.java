package uk.co.idv.uk.domain.entities.lockout;

import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;
import uk.co.idv.domain.entities.lockout.state.LockoutStateCalculator;

import java.util.UUID;

public class RsaLockoutPolicy extends DefaultLockoutPolicy {

    public RsaLockoutPolicy(final UUID id, final String aliasType) {
        super(id, lockoutLevel(aliasType), stateCalculator(), recordAttemptStrategy());
    }

    private static LockoutLevel lockoutLevel(final String aliasType) {
        return new RsaLockoutLevel(aliasType);
    }

    private static LockoutStateCalculator stateCalculator() {
        return new RsaLockoutStateCalculator();
    }

    private static RecordAttemptStrategy recordAttemptStrategy() {
        return new RecordEveryAttempt();
    }

}
