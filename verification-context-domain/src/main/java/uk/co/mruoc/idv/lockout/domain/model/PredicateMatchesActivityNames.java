package uk.co.mruoc.idv.lockout.domain.model;

import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

public class PredicateMatchesActivityNames implements Predicate<LockoutRequest> {

    private final Collection<String> activityNames;

    public PredicateMatchesActivityNames(final String... activityNames) {
        this(Arrays.asList(activityNames));
    }

    public PredicateMatchesActivityNames(final Collection<String> activityNames) {
        this.activityNames = activityNames;
    }

    @Override
    public boolean test(final LockoutRequest provider) {
        return activityNames.isEmpty() || activityNames.contains(provider.getActivityName());
    }

}
