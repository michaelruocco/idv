package uk.co.mruoc.idv.lockout.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.lockout.domain.model.PolicyAppliesToRequestPredicate.DefaultPolicyPredicateBuilder;
import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Getter
public class LockoutPolicyParameters {

    private final UUID id;
    private final String lockoutType;
    private final String recordAttemptStrategyType;
    private final Collection<String> channelIds;
    private final Collection<String> activityNames;
    private final Collection<String> aliasTypes;
    private final LockoutStateCalculator stateCalculator;

    public LockoutPolicy toPolicy() {
        return DefaultLockoutPolicy.builder()
                .parameters(this)
                .appliesToPolicy(buildAppliesToPolicyPredicate())
                .recordAttemptStrategy(buildRecordAttemptStrategy())
                .stateCalculator(stateCalculator)
                .build();
    }

    private Predicate<LockoutRequest> buildAppliesToPolicyPredicate() {
        return new DefaultPolicyPredicateBuilder()
                .channelIds(channelIds)
                .activityNames(activityNames)
                .aliasTypes(aliasTypes)
                .build();
    }

    private RecordAttemptStrategy buildRecordAttemptStrategy() {
        switch (recordAttemptStrategyType) {
            case RecordEveryAttempt.TYPE: return new RecordEveryAttempt();
            case RecordNever.TYPE: return new RecordNever();
            case RecordOnMethodComplete.TYPE: return new RecordOnMethodComplete();
            case RecordOnSequenceComplete.TYPE: return new RecordOnSequenceComplete();
            default: throw new RecordAttemptStrategyNotSupportedException(recordAttemptStrategyType);
        }
    }

    public static class RecordAttemptStrategyNotSupportedException extends RuntimeException {

        public RecordAttemptStrategyNotSupportedException(final String type) {
            super(type);
        }

    }

}
