package uk.co.mruoc.idv.lockout.domain.model;

import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

import java.util.Arrays;
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

    public static class DefaultPolicyPredicateBuilder {

        private Collection<String> channelIds;
        private Collection<String> activityNames;
        private Collection<String> aliasTypes;

        public DefaultPolicyPredicateBuilder channelIds(final String... channelIds) {
            return channelIds(Arrays.asList(channelIds));
        }

        public DefaultPolicyPredicateBuilder channelIds(final Collection<String> channelIds) {
            this.channelIds = channelIds;
            return this;
        }

        public DefaultPolicyPredicateBuilder activityNames(final String... activityNames) {
            return activityNames(Arrays.asList(activityNames));
        }

        public DefaultPolicyPredicateBuilder activityNames(final Collection<String> activityNames) {
            this.activityNames = activityNames;
            return this;
        }

        public DefaultPolicyPredicateBuilder aliasTypes(final String... aliasTypes) {
            return aliasTypes(Arrays.asList(aliasTypes));
        }

        public DefaultPolicyPredicateBuilder aliasTypes(final Collection<String> aliasTypes) {
            this.aliasTypes = aliasTypes;
            return this;
        }

        public Predicate<LockoutRequest> build() {
            return new PolicyAppliesToRequestPredicate(
                    new PredicateMatchesChannel(channelIds),
                    new PredicateMatchesActivityNames(activityNames),
                    new PredicateMatchesAliasTypes(aliasTypes)
            );
        }

    }

}
