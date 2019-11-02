package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.lockout.domain.model.DefaultLockoutPolicy;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicy;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.LockoutRequestPredicateFactory;
import uk.co.mruoc.idv.lockout.domain.model.RecordAttemptStrategyFactory;
import uk.co.mruoc.idv.lockout.domain.model.LockoutStateCalculatorFactory;

@Builder
public class LockoutPolicyParametersConverter {

    private final LockoutRequestPredicateFactory predicateFactory;
    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory;
    private final LockoutStateCalculatorFactory lockoutStateCalculatorFactory;

    public LockoutPolicy toPolicy(final LockoutPolicyParameters parameters) {
        return DefaultLockoutPolicy.builder()
                .parameters(parameters)
                .appliesToPolicy(predicateFactory.build(parameters))
                .recordAttemptStrategy(recordAttemptStrategyFactory.build(parameters.getRecordAttemptStrategyType()))
                .stateCalculator(lockoutStateCalculatorFactory.build(parameters))
                .build();
    }

}
