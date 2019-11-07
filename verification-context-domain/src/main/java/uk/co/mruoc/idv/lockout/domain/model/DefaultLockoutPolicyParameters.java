package uk.co.mruoc.idv.lockout.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class DefaultLockoutPolicyParameters implements LockoutPolicyParameters {

    private final UUID id;
    private final String lockoutType;
    private final String recordAttemptStrategyType;
    private final Collection<String> channelIds;
    private final Collection<String> activityNames;
    private final Collection<String> aliasTypes;

    @Override
    public boolean appliesTo(final LockoutRequest request) {
        return matchesChannel(request) &&
                matchesActivityName(request) &&
                matchesAliasType(request);
    }

    @Override
    public boolean useAliasLevelLocking() {
        return !aliasTypes.isEmpty();
    }

    private boolean matchesChannel(final LockoutRequest request) {
        return channelIds.contains(request.getChannelId());
    }

    private boolean matchesActivityName(final LockoutRequest request) {
        return activityNames.contains(request.getActivityName());
    }

    private boolean matchesAliasType(final LockoutRequest request) {
        if (!useAliasLevelLocking()) {
            return true;
        }
        return aliasTypes.contains(request.getAliasType());
    }

}
