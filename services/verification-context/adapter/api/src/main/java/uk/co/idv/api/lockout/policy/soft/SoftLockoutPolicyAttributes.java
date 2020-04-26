package uk.co.idv.api.lockout.policy.soft;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import uk.co.idv.api.lockout.policy.DefaultLockoutPolicyAttributes;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockIntervals;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutStateCalculator;

import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = true)
public class SoftLockoutPolicyAttributes extends DefaultLockoutPolicyAttributes {

    private final SoftLockIntervals intervals;

    @Builder
    public SoftLockoutPolicyAttributes(final UUID id,
                                       final String recordAttempts,
                                       final LockoutLevel lockoutLevel,
                                       final SoftLockIntervals intervals) {
        super(id,
                SoftLockoutStateCalculator.TYPE,
                recordAttempts,
                lockoutLevel);
        this.intervals = intervals;
    }

}
