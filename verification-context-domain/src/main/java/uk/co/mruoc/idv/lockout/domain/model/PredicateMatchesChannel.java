package uk.co.mruoc.idv.lockout.domain.model;

import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

public class PredicateMatchesChannel implements Predicate<LockoutRequest> {

    private final Collection<String> channelIds;

    public PredicateMatchesChannel(final String... channelIds) {
        this(Arrays.asList(channelIds));
    }

    public PredicateMatchesChannel(final Collection<String> channelIds) {
        this.channelIds = channelIds;
    }

    @Override
    public boolean test(final LockoutRequest request) {
        return channelIds.isEmpty() || channelIds.contains(request.getChannelId());
    }

}
