package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.lockout.domain.model.DefaultLockoutPolicy;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicy;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.LockoutStateCalculatorFactory;
import uk.co.mruoc.idv.lockout.domain.model.RecordAttemptStrategyFactory;

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
