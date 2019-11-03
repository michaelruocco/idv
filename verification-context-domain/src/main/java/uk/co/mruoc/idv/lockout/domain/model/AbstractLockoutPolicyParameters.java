package uk.co.mruoc.idv.lockout.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;

import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public abstract class AbstractLockoutPolicyParameters {

    private final UUID id;
    private final String lockoutType;
    private final String recordAttemptStrategyType;
    private final Collection<String> channelIds;
    private final Collection<String> activityNames;
    private final Collection<String> aliasTypes;

    public String getChannelId(final int index) {
        return IterableUtils.get(channelIds, index);
    }

    public String getActivityName(final int index) {
        return IterableUtils.get(activityNames, index);
    }

    public String getAliasType(final int index) {
        return IterableUtils.get(aliasTypes, index);
    }

}
