package uk.co.mruoc.idv.lockout.domain.model;

import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

import java.util.Collection;
import java.util.function.Predicate;

public class PolicyAppliesToRequestPredicate implements Predicate<LockoutRequest> {

    private final Predicate<LockoutRequest> matchesChannel;
    private final Predicate<LockoutRequest> matchesActivityName;
    private final Predicate<LockoutRequest> matchesAliasType;

    private PolicyAppliesToRequestPredicate(final Predicate<LockoutRequest> matchesChannel,
                                            final Predicate<LockoutRequest> matchesActivityName,
                                            final Predicate<LockoutRequest> matchesAliasType) {
        this.matchesChannel = matchesChannel;
        this.matchesActivityName = matchesActivityName;
        this.matchesAliasType = matchesAliasType;
    }

    @Override
    public boolean test(final LockoutRequest request) {
        return matchesChannel
                .and(matchesActivityName)
                .and(matchesAliasType)
                .test(request);
    }

    public static class PolicyAppliesToRequestPredicateBuilder {

        private Collection<String> channelIds;
        private Collection<String> activityNames;
        private Collection<String> aliasTypes;

        public PolicyAppliesToRequestPredicateBuilder channelIds(final Collection<String> channelIds) {
            this.channelIds = channelIds;
            return this;
        }

        public PolicyAppliesToRequestPredicateBuilder activityNames(final Collection<String> activityNames) {
            this.activityNames = activityNames;
            return this;
        }

        public PolicyAppliesToRequestPredicateBuilder aliasTypes(final Collection<String> aliasTypes) {
            this.aliasTypes = aliasTypes;
            return this;
        }

        public PolicyAppliesToRequestPredicate build() {
            return new PolicyAppliesToRequestPredicate(
                    new PredicateMatchesChannel(channelIds),
                    new PredicateMatchesActivityNames(activityNames),
                    new PredicateMatchesAliasTypes(aliasTypes)
            );
        }

    }

}
