package uk.co.mruoc.idv.lockout.domain.service;

import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.MaxAttemptsLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.service.PolicyAppliesToRequestPredicate.DefaultPolicyPredicateBuilder;

import java.util.function.Predicate;

@RequiredArgsConstructor
public class LockoutPolicyFactory {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory;

    public LockoutPolicy build(final LockoutPolicyParameters parameters) {
        return LockoutPolicyDefault.builder()
                .parameters(parameters)
                .appliesToPolicy(buildAppliesToPolicyPredicate(parameters))
                .recordAttemptStrategy(buildRecordAttemptStrategy(parameters))
                .stateCalculator(buildStateCalculator(parameters))
                .build();
    }

    private LockoutStateCalculator buildStateCalculator(final LockoutPolicyParameters parameters) {
        final String type = parameters.getLockoutType();
        if (MaxAttemptsLockoutPolicyParameters.TYPE.equals(type)) {
            return buildStateCalculator((MaxAttemptsLockoutPolicyParameters) parameters);
        }
        throw new LockoutTypeNotSupportedException(type);
    }

    private LockoutStateCalculator buildStateCalculator(final MaxAttemptsLockoutPolicyParameters parameters) {
        return new MaxAttemptsLockoutStateCalculator(parameters.getMaxNumberOfAttempts());
    }

    private Predicate<LockoutRequest> buildAppliesToPolicyPredicate(final LockoutPolicyParameters parameters) {
        return new DefaultPolicyPredicateBuilder()
                .channelIds(parameters.getChannelIds())
                .activityNames(parameters.getActivityNames())
                .aliasTypes(parameters.getAliasTypes())
                .build();
    }

    private RecordAttemptStrategy buildRecordAttemptStrategy(final LockoutPolicyParameters parameters) {
        return recordAttemptStrategyFactory.build(parameters.getRecordAttemptStrategyType());
    }

    public static class LockoutTypeNotSupportedException extends RuntimeException {

        public LockoutTypeNotSupportedException(final String type) {
            super(type);
        }

    }

}
