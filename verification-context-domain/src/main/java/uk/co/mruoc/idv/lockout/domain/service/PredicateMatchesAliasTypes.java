package uk.co.mruoc.idv.lockout.domain.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

public class PredicateMatchesAliasTypes implements Predicate<LockoutRequest> {

    private final Collection<String> aliasTypes;

    public PredicateMatchesAliasTypes(final String... aliasTypes) {
        this(Arrays.asList(aliasTypes));
    }

    public PredicateMatchesAliasTypes(final Collection<String> aliasTypes) {
        this.aliasTypes = aliasTypes;
    }

    @Override
    public boolean test(final LockoutRequest provider) {
        return aliasTypes.isEmpty() || aliasTypes.contains(provider.getAliasType());
    }

}
