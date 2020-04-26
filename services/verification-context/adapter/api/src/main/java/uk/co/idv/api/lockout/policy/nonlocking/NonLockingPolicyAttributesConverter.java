package uk.co.idv.api.lockout.policy.nonlocking;

import lombok.RequiredArgsConstructor;
import uk.co.idv.api.lockout.policy.DefaultLockoutPolicyAttributes;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributes;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributesConverter;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;

@RequiredArgsConstructor
public class NonLockingPolicyAttributesConverter implements LockoutPolicyAttributesConverter {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory;

    @Override
    public boolean supports(String type) {
        return NonLockingLockoutStateCalculator.TYPE.equals(type);
    }

    public LockoutPolicy toPolicy(final LockoutPolicyAttributes attributes) {
        return new NonLockingLockoutPolicy(
                attributes.getId(),
                attributes.getLockoutLevel(),
                recordAttemptStrategyFactory.build(attributes.getRecordAttempts())
        );
    }

    public LockoutPolicyAttributes toAttributes(final LockoutPolicy policy) {
        return new DefaultLockoutPolicyAttributes(
                policy.getId(),
                policy.getType(),
                policy.getRecordAttemptStrategyType(),
                policy.getLevel()
        );
    }

}
