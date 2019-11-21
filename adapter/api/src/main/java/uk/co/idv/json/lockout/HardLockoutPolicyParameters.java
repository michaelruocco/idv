package uk.co.idv.json.lockout;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;

import java.util.UUID;

@Getter
public class HardLockoutPolicyParameters extends DefaultLockoutPolicyParameters {

    private final int maxNumberOfAttempts;

    @Builder
    public HardLockoutPolicyParameters(final UUID id,
                                       final String recordAttempts,
                                       final LockoutLevel lockoutLevel,
                                       final int maxNumberOfAttempts) {
        super(id,
                HardLockoutStateCalculator.TYPE,
                recordAttempts,
                lockoutLevel);
        this.maxNumberOfAttempts = maxNumberOfAttempts;
    }

}
