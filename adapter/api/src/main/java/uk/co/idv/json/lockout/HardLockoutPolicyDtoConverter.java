package uk.co.idv.json.lockout;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;

@RequiredArgsConstructor
public class HardLockoutPolicyDtoConverter implements LockoutPolicyParametersConverter {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory;

    @Override
    public boolean supports(String type) {
        return HardLockoutStateCalculator.TYPE.equals(type);
    }

    public LockoutPolicy toPolicy(final LockoutPolicyDto parameters) {
        final HardLockoutPolicyDto hardLockoutParameters = (HardLockoutPolicyDto) parameters;
        return new HardLockoutPolicy(
                hardLockoutParameters.getId(),
                hardLockoutParameters.getLockoutLevel(),
                recordAttemptStrategyFactory.build(hardLockoutParameters.getRecordAttempts()),
                hardLockoutParameters.getMaxNumberOfAttempts()
        );
    }

    public LockoutPolicyDto toParameters(final LockoutPolicy policy) {
        final HardLockoutPolicy hardLockoutPolicy = (HardLockoutPolicy) policy;
        return HardLockoutPolicyDto.builder()
                .id(hardLockoutPolicy.getId())
                .recordAttempts(hardLockoutPolicy.getRecordAttemptStrategyType())
                .maxNumberOfAttempts(hardLockoutPolicy.getMaxNumberOfAttempts())
                .lockoutLevel(hardLockoutPolicy.getLockoutLevel())
                .build();
    }

}
