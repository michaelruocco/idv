package uk.co.idv.domain.usecases.lockout;

import lombok.Builder;
import uk.co.idv.domain.entities.lockout.DefaultLockoutPolicy;
import uk.co.idv.domain.entities.lockout.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.LockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.LockoutStateCalculatorFactory;
import uk.co.idv.domain.entities.lockout.RecordAttemptStrategyFactory;

@Builder
public class LockoutPolicyParametersConverter {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory;
    private final LockoutStateCalculatorFactory lockoutStateCalculatorFactory;

    public LockoutPolicy toPolicy(final LockoutPolicyParameters parameters) {
        return DefaultLockoutPolicy.builder()
                .parameters(parameters)
                .recordAttemptStrategy(recordAttemptStrategyFactory.build(parameters.getRecordAttemptStrategyType()))
                .stateCalculator(lockoutStateCalculatorFactory.build(parameters))
                .build();
    }

}
