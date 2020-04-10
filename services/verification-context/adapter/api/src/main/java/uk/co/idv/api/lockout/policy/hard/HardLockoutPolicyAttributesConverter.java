package uk.co.idv.api.lockout.policy.hard;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributes;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributesConverter;

@RequiredArgsConstructor
public class HardLockoutPolicyAttributesConverter implements LockoutPolicyAttributesConverter {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory;

    @Override
    public boolean supports(final String type) {
        return HardLockoutStateCalculator.TYPE.equals(type);
    }

    public LockoutPolicy toPolicy(final LockoutPolicyAttributes attributes) {
        final HardLockoutPolicyAttributes hardLockoutAttributes = (HardLockoutPolicyAttributes) attributes;
        return new HardLockoutPolicy(
                hardLockoutAttributes.getId(),
                hardLockoutAttributes.getLockoutLevel(),
                recordAttemptStrategyFactory.build(hardLockoutAttributes.getRecordAttempts()),
                hardLockoutAttributes.getMaxNumberOfAttempts()
        );
    }

    public LockoutPolicyAttributes toAttributes(final LockoutPolicy policy) {
        final HardLockoutPolicy hardLockoutPolicy = (HardLockoutPolicy) policy;
        return HardLockoutPolicyAttributes.builder()
                .id(hardLockoutPolicy.getId())
                .recordAttempts(hardLockoutPolicy.getRecordAttemptStrategyType())
                .maxNumberOfAttempts(hardLockoutPolicy.getMaxNumberOfAttempts())
                .lockoutLevel(hardLockoutPolicy.getLockoutLevel())
                .build();
    }

}
