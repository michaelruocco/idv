package uk.co.idv.api.lockout.policy.soft;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributes;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributesConverter;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutPolicy;

@Builder
@RequiredArgsConstructor
public class SoftLockoutPolicyAttributesConverter implements LockoutPolicyAttributesConverter {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory;

    @Override
    public boolean supports(final String type) {
        return SoftLockoutStateCalculator.TYPE.equals(type);
    }

    public LockoutPolicy toPolicy(final LockoutPolicyAttributes attributes) {
        final SoftLockoutPolicyAttributes softLockoutAttributes = (SoftLockoutPolicyAttributes) attributes;
        return new SoftLockoutPolicy(
                softLockoutAttributes.getId(),
                softLockoutAttributes.getLevel(),
                recordAttemptStrategyFactory.build(softLockoutAttributes.getRecordAttempts()),
                softLockoutAttributes.getIntervals()
        );
    }

    public LockoutPolicyAttributes toAttributes(final LockoutPolicy policy) {
        final SoftLockoutPolicy softLockoutPolicy = (SoftLockoutPolicy) policy;
        return SoftLockoutPolicyAttributes.builder()
                .id(softLockoutPolicy.getId())
                .recordAttempts(softLockoutPolicy.getRecordAttemptStrategyType())
                .lockoutLevel(softLockoutPolicy.getLevel())
                .intervals(softLockoutPolicy.getIntervals())
                .build();
    }



}
