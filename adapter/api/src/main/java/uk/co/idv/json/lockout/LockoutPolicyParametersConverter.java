package uk.co.idv.json.lockout;

import lombok.Builder;
import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.domain.entities.lockout.state.MaxAttemptsLockoutStateCalculator;

import java.util.Collection;
import java.util.stream.Collectors;

@Builder
public class LockoutPolicyParametersConverter {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory;
    private final LockoutStateCalculatorFactory lockoutStateCalculatorFactory;

    public LockoutPolicy toPolicy(final LockoutPolicyParameters parameters) {
        return DefaultLockoutPolicy.builder()
                .level(parameters.getLockoutLevel())
                .recordAttemptStrategy(recordAttemptStrategyFactory.build(parameters.getRecordAttemptStrategyType()))
                .stateCalculator(lockoutStateCalculatorFactory.build(parameters))
                .build();
    }

    public Collection<LockoutPolicyParameters> toParameters(final Collection<LockoutPolicy> policies) {
        return policies.stream()
                .map(this::toParameters)
                .collect(Collectors.toList());
    }

    public LockoutPolicyParameters toParameters(final LockoutPolicy policy) {
        return MaxAttemptsLockoutPolicyParameters.builder()
                .id(policy.getId())
                .recordAttemptStrategyType(policy.getRecordAttemptStrategyType())
                .maxNumberOfAttempts(((MaxAttemptsLockoutStateCalculator) policy.getStateCalculator()).getMaxAttempts())
                .lockoutLevel(policy.getLockoutLevel())
                .build();
    }

}
