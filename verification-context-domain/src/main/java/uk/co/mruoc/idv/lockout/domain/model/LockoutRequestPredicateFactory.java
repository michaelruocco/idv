package uk.co.mruoc.idv.lockout.domain.model;

import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

import java.util.function.Predicate;

public class LockoutRequestPredicateFactory {

    public Predicate<LockoutRequest> build(final LockoutPolicyParameters parameters) {
        return new PolicyAppliesToRequestPredicate.DefaultPolicyPredicateBuilder()
                .channelIds(parameters.getChannelIds())
                .activityNames(parameters.getActivityNames())
                .aliasTypes(parameters.getAliasTypes())
                .build();
    }

}
