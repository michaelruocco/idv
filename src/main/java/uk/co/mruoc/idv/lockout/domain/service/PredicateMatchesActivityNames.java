package uk.co.mruoc.idv.lockout.domain.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

public class PredicateMatchesActivityNames implements Predicate<LockoutPolicyRequest> {

    private final Collection<String> activityNames;

    public PredicateMatchesActivityNames(final String... activityNames) {
        this(Arrays.asList(activityNames));
    }

    public PredicateMatchesActivityNames(final Collection<String> activityNames) {
        this.activityNames = activityNames;
    }

    @Override
    public boolean test(final LockoutPolicyRequest provider) {
        return activityNames.isEmpty() || activityNames.contains(provider.getActivityName());
    }

}
