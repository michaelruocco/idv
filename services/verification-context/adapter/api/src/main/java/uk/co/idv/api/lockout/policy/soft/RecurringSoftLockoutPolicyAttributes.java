package uk.co.idv.api.lockout.policy.soft;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import uk.co.idv.api.lockout.policy.DefaultLockoutPolicyAttributes;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutStateCalculator;

import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = true)
public class RecurringSoftLockoutPolicyAttributes extends DefaultLockoutPolicyAttributes {

    private final SoftLockIntervalDto interval;

    @Builder
    public RecurringSoftLockoutPolicyAttributes(final UUID id,
                                                final String recordAttempts,
                                                final LockoutLevel lockoutLevel,
                                                final SoftLockIntervalDto interval) {
        super(id,
                RecurringSoftLockoutStateCalculator.TYPE,
                recordAttempts,
                lockoutLevel);
        this.interval = interval;
    }

}
