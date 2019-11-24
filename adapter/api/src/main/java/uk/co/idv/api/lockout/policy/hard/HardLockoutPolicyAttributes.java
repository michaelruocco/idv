package uk.co.idv.api.lockout.policy.hard;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import uk.co.idv.api.lockout.policy.DefaultLockoutPolicyAttributes;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;

import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = true)
public class HardLockoutPolicyAttributes extends DefaultLockoutPolicyAttributes {

    private final int maxNumberOfAttempts;

    @Builder
    public HardLockoutPolicyAttributes(final UUID id,
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
