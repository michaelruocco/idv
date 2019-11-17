package uk.co.idv.domain.entities.lockout.policy;

import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;
import uk.co.idv.domain.entities.lockout.state.LockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.state.MaxAttemptsLockoutStateCalculator;

import java.util.UUID;

public class LockoutPolicyMother {

    public static LockoutPolicy maxAttemptsPolicy() {
        return maxAttemptsPolicy(UUID.randomUUID());
    }

    public static LockoutPolicy maxAttemptsPolicy(final UUID id) {
        return DefaultLockoutPolicy.builder()
                .level(LockoutLevelMother.aliasLockoutLevel())
                .stateCalculator(buildStateCalculator())
                .recordAttemptStrategy(buildRecordAttemptStrategy())
                .id(id)
                .build();
    }

    private static LockoutStateCalculator buildStateCalculator() {
        return new MaxAttemptsLockoutStateCalculator(3);
    }

    private static RecordAttemptStrategy buildRecordAttemptStrategy() {
        return new RecordEveryAttempt();
    }

}
