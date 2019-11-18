package uk.co.idv.json.lockout;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.maxattempts.MaxAttemptsLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.domain.entities.lockout.policy.maxattempts.MaxAttemptsLockoutStateCalculator;

@RequiredArgsConstructor
public class MaxAttemptsLockoutPolicyParametersConverter implements LockoutPolicyParametersConverter {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory;

    @Override
    public boolean supports(String type) {
        return MaxAttemptsLockoutStateCalculator.TYPE.equals(type);
    }

    public LockoutPolicy toPolicy(final LockoutPolicyParameters parameters) {
        final MaxAttemptsLockoutPolicyParameters maxAttemptsParameters = (MaxAttemptsLockoutPolicyParameters) parameters;
        return new MaxAttemptsLockoutPolicy(
                maxAttemptsParameters.getId(),
                maxAttemptsParameters.getLockoutLevel(),
                recordAttemptStrategyFactory.build(maxAttemptsParameters.getRecordAttempts()),
                maxAttemptsParameters.getMaxNumberOfAttempts()
        );
    }

    public LockoutPolicyParameters toParameters(final LockoutPolicy policy) {
        final MaxAttemptsLockoutPolicy maxAttemptsPolicy = (MaxAttemptsLockoutPolicy) policy;
        return MaxAttemptsLockoutPolicyParameters.builder()
                .id(maxAttemptsPolicy.getId())
                .recordAttempts(maxAttemptsPolicy.getRecordAttemptStrategyType())
                .maxNumberOfAttempts(maxAttemptsPolicy.getMaxAttempts())
                .lockoutLevel(maxAttemptsPolicy.getLockoutLevel())
                .build();
    }

}
