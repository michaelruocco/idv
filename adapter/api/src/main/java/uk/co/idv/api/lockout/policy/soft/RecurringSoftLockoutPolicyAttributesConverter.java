package uk.co.idv.api.lockout.policy.soft;

import lombok.RequiredArgsConstructor;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributes;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributesConverter;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutStateCalculator;

@RequiredArgsConstructor
public class RecurringSoftLockoutPolicyAttributesConverter implements LockoutPolicyAttributesConverter {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory;
    private final SoftLockIntervalDtoConverter softLockIntervalDtoConverter;

    @Override
    public boolean supports(final String type) {
        return RecurringSoftLockoutStateCalculator.TYPE.equals(type);
    }

    public LockoutPolicy toPolicy(final LockoutPolicyAttributes attributes) {
        final RecurringSoftLockoutPolicyAttributes softLockoutAttributes = (RecurringSoftLockoutPolicyAttributes) attributes;
        return new RecurringSoftLockoutPolicy(
                softLockoutAttributes.getId(),
                softLockoutAttributes.getLockoutLevel(),
                recordAttemptStrategyFactory.build(softLockoutAttributes.getRecordAttempts()),
                softLockIntervalDtoConverter.toInterval(softLockoutAttributes.getInterval())
        );
    }

    public LockoutPolicyAttributes toAttributes(final LockoutPolicy policy) {
        final RecurringSoftLockoutPolicy softLockoutPolicy = (RecurringSoftLockoutPolicy) policy;
        return RecurringSoftLockoutPolicyAttributes.builder()
                .id(softLockoutPolicy.getId())
                .recordAttempts(softLockoutPolicy.getRecordAttemptStrategyType())
                .lockoutLevel(softLockoutPolicy.getLockoutLevel())
                .interval(softLockIntervalDtoConverter.toDto(softLockoutPolicy.getInterval()))
                .build();
    }

}